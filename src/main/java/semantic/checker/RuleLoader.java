package semantic.checker;

import semantic.SemanticException;
import semantic.model.function.Function;
import semantic.model.type.ArrayType;
import semantic.model.type.DataType;
import semantic.model.type.FunctionType;
import semantic.model.type.NumericType;
import semantic.model.variable.Variable;
import semantic.tree.Leaf;
import semantic.tree.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static semantic.model.type.NumericType.*;
import static semantic.model.util.ConstantUtil.requireIntValue;

public class RuleLoader {


    private final Map<String, Map<List<String>, Rule>> rules;


    public RuleLoader(Map<String, Map<List<String>, Rule>> rules) {
        this.rules = rules;
    }

    private void addRule(String productionLeftSide, List<String> productionRightSide, Rule rule){
        Map<List<String>, Rule> innerMap = rules.getOrDefault(productionLeftSide, new HashMap<>());

        innerMap.put(productionRightSide, rule);

        rules.put(productionLeftSide, innerMap);
    }

    public void loadRules(){

        // <primarni_izraz>>

        addRule("<primarni_izraz>", List.of(
                "IDN"
        ), (node, checker, scope) -> {
            Leaf IDN = (Leaf) node.getChild(0);

            scope.requireDeclaredVariable(IDN.getName());

            Variable idnVariable = scope.getVariable(IDN.getName());

            node.setProperty("tip", idnVariable.getType());
            node.setProperty("l-izraz", idnVariable.isLValue());
        });

        addRule("<primarni_izraz>", List.of(
                "BROJ"
        ), (node, checker, scope) -> {
            Leaf BROJ = (Leaf) node.getChild(0);

            requireIntValue(BROJ.getSourceText());

            node.setProperty("tip", INT);
            node.setProperty("l-izraz", Boolean.FALSE);
        });

        // TODO: continue for <primarni_izraz>

        addRule("<primarni_izraz>", List.of(
                "L_ZAGRADA",
                "<izraz>",
                "D_ZAGRADA"
        ), (node, checker, scope) -> {
            Node izraz = (Node) node.getChild(1);

            checker.check(izraz);

            node.setProperty("tip", izraz.getProperty("tip"));
            node.setProperty("l-izraz", izraz.getProperty("l-izraz"));
        });


        // <postfiks_izraz>

        addRule("<postfiks_izraz>", List.of(
                "<primarni_izraz>"
        ), (node, checker, scope) -> {
            Node primarni_izraz = (Node) node.getChild(0);

            checker.check(primarni_izraz);

            node.setProperty("tip", primarni_izraz.getProperty("tip"));
            node.setProperty("l-izraz", primarni_izraz.getProperty("l-izraz"));
        });

        // TODO: provjeri pravila ove produkcije
        addRule("<postfiks_izraz>", List.of(
                "<postfiks_izraz>",
                "L_UGL_ZAGRADA",
                "<izraz>",
                "D_UGL_ZAGRADA"
        ), (node, checker, scope) -> {
            Node postfiks_izraz = (Node) node.getChild(0);
            Node izraz = (Node) node.getChild(2);

            checker.check(postfiks_izraz);
            if(!(postfiks_izraz.getProperty("tip") instanceof ArrayType)){
                throw new SemanticException();
            }
            checker.check(izraz);
            if(!((NumericType) izraz.getProperty("tip")).implicitlyCastableTo(INT)){
                throw new SemanticException();
            }

            node.setProperty("tip", ((ArrayType) postfiks_izraz.getProperty("tip")).getNumericType());
            node.setProperty("l-izraz", !((NumericType) postfiks_izraz.getProperty("tip")).isConst());
        });

        addRule("<postfiks_izraz>", List.of(
                "<postfiks_izraz>",
                 "L_ZAGRADA",
                "D_ZAGRADA"
        ), (node, checker, scope) -> {
            Node postfiks_izraz = (Node) node.getChild(0);

            checker.check(postfiks_izraz);

            FunctionType functionType = (FunctionType) postfiks_izraz.getProperty("tip");

            if(functionType.getReturnType() != VOID){
                throw new SemanticException();
            }

            node.setProperty("tip", functionType.getReturnType());
            node.setProperty("l-izraz", Boolean.FALSE);
        });

        //TODO nastavi


        // <unarni_izraz>

        addRule("<unarni_izraz>", List.of(
                "<postfiks_izraz>"
        ), (node, checker, scope) -> {
            Node postfiks_izraz = (Node) node.getChild(0);

            checker.check(postfiks_izraz);

            node.setProperty("tip", postfiks_izraz.getProperty("tip"));
            node.setProperty("l-izraz", postfiks_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi

        // <unarni_operator>

        //TODO nastavi

        // <cast_izraz>

        addRule("<cast_izraz>", List.of(
                "<unarni_izraz>"
        ), (node, checker, scope) -> {
            Node unarni_izraz = (Node) node.getChild(0);

            checker.check(unarni_izraz);

            node.setProperty("tip", unarni_izraz.getProperty("tip"));
            node.setProperty("l-izraz", unarni_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi

        // <ime_tipa>

        addRule("<ime_tipa>", List.of(
                "<specifikator_tipa>"
        ), (node, checker, scope) -> {
            Node specifikator_tipa = (Node) node.getChild(0);

            checker.check(specifikator_tipa);

            node.setProperty("tip", specifikator_tipa.getProperty("tip"));
        });

        //TODO nastavi


        // <specifikator_tipa>

        addRule("<specifikator_tipa>", List.of(
                "KR_VOID"
        ), (node, checker, scope) -> {
            node.setProperty("tip", VOID);
        });

        addRule("<specifikator_tipa>", List.of(
                "KR_CHAR"
        ), (node, checker, scope) -> {
            node.setProperty("tip", CHAR);
        });

        addRule("<specifikator_tipa>", List.of(
                "KR_INT"
        ), (node, checker, scope) -> {
            node.setProperty("tip", INT);
        });


        // <multiplikativni_izraz>

        addRule("<multiplikativni_izraz>", List.of(
                "<cast_izraz>"
        ), (node, checker, scope) -> {
            Node cast_izraz = (Node) node.getChild(0);

            checker.check(cast_izraz);

            node.setProperty("tip", cast_izraz.getProperty("tip"));
            node.setProperty("l-izraz", cast_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi


        // <aditivni_izraz>

        addRule("<aditivni_izraz>", List.of(
                "<multiplikativni_izraz>"
        ), (node, checker, scope) -> {
            Node multiplikativni_izraz = (Node) node.getChild(0);

            checker.check(multiplikativni_izraz);

            node.setProperty("tip", multiplikativni_izraz.getProperty("tip"));
            node.setProperty("l-izraz", multiplikativni_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi


        // <odnosni_izraz>

        addRule("<odnosni_izraz>", List.of(
                "<aditivni_izraz>"
        ), (node, checker, scope) -> {
            Node aditivni_izraz = (Node) node.getChild(0);

            checker.check(aditivni_izraz);

            node.setProperty("tip", aditivni_izraz.getProperty("tip"));
            node.setProperty("l-izraz", aditivni_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi


        // <jednakosni_izraz>

        addRule("<jednakosni_izraz>", List.of(
                "<odnosni_izraz>"
        ), (node, checker, scope) -> {
            Node odnosni_izraz = (Node) node.getChild(0);

            checker.check(odnosni_izraz);

            node.setProperty("tip", odnosni_izraz.getProperty("tip"));
            node.setProperty("l-izraz", odnosni_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi


        // <bin_i_izraz>

        addRule("<bin_i_izraz>", List.of(
                "<jednakosni_izraz>"
        ), (node, checker, scope) -> {
            Node jednakosni_izraz = (Node) node.getChild(0);

            checker.check(jednakosni_izraz);

            node.setProperty("tip", jednakosni_izraz.getProperty("tip"));
            node.setProperty("l-izraz", jednakosni_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi



        // <bin_xili_izraz>

        addRule("<bin_xili_izraz>", List.of(
                "<bin_i_izraz>"
        ), (node, checker, scope) -> {
            Node bin_i_izraz = (Node) node.getChild(0);

            checker.check(bin_i_izraz);

            node.setProperty("tip", bin_i_izraz.getProperty("tip"));
            node.setProperty("l-izraz", bin_i_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi


        // <bin_ili_izraz>

        addRule("<bin_ili_izraz>", List.of(
                "<bin_xili_izraz>"
        ), (node, checker, scope) -> {
            Node bin_xili_izraz = (Node) node.getChild(0);

            checker.check(bin_xili_izraz);

            node.setProperty("tip", bin_xili_izraz.getProperty("tip"));
            node.setProperty("l-izraz", bin_xili_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi



        // <log_i_izraz>

        addRule("<log_i_izraz>", List.of(
                "<bin_ili_izraz>"
        ), (node, checker, scope) -> {
            Node bin_ili_izraz = (Node) node.getChild(0);

            checker.check(bin_ili_izraz);

            node.setProperty("tip", bin_ili_izraz.getProperty("tip"));
            node.setProperty("l-izraz", bin_ili_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi



        // <log_ili_izraz>

        addRule("<log_ili_izraz>", List.of(
                "<log_i_izraz>"
        ), (node, checker, scope) -> {
            Node log_i_izraz = (Node) node.getChild(0);

            checker.check(log_i_izraz);

            node.setProperty("tip", log_i_izraz.getProperty("tip"));
            node.setProperty("l-izraz", log_i_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi


        // <izraz_pridruzivanja>

        addRule("<izraz_pridruzivanja>", List.of(
                "<log_ili_izraz>"
        ), (node, checker, scope) -> {
            Node log_ili_izraz = (Node) node.getChild(0);

            checker.check(log_ili_izraz);

            node.setProperty("tip", log_ili_izraz.getProperty("tip"));
            node.setProperty("l-izraz", log_ili_izraz.getProperty("l-izraz"));
        });

        //TODO nastavi


        // <izraz>

        addRule("<izraz>", List.of(
                "<izraz_pridruzivanja>"
        ), (node, checker, scope) -> {
            Node izraz_pridruzivanja = (Node) node.getChild(0);

            checker.check(izraz_pridruzivanja);

            node.setProperty("tip", izraz_pridruzivanja.getProperty("tip"));
            node.setProperty("l-izraz", izraz_pridruzivanja.getProperty("l-izraz"));
        });

        //TODO nastavi



        // <slozena_naredba>

        addRule("<slozena_naredba>", List.of(
                "L_VIT_ZAGRADA",
                "<lista_naredbi>",
                "D_VIT_ZAGRADA"
        ), (node, checker, scope) -> {
            Node lista_naredbi = (Node) node.getChild(1);

            checker.check(lista_naredbi);
          });

        //TODO nastavi


        // <lista_naredbi>

        addRule("<lista_naredbi>", List.of(
                "<naredba>"
        ), (node, checker, scope) -> {
            Node naredba = (Node) node.getChild(0);

            checker.check(naredba);
         });

        //TODO nastavi



        // <naredba>

        addRule("<naredba>", List.of(
                "<slozena_naredba>"
        ), (node, checker, scope) -> {
            Node slozena_naredba = (Node) node.getChild(0);

            checker.check(slozena_naredba);
        });

        addRule("<naredba>", List.of(
                "<izraz_naredba>"
        ), (node, checker, scope) -> {
            Node izraz_naredba = (Node) node.getChild(0);

            checker.check(izraz_naredba);
        });

        addRule("<naredba>", List.of(
                "<naredba_grananja>"
        ), (node, checker, scope) -> {
            Node naredba_grananja = (Node) node.getChild(0);

            checker.check(naredba_grananja);
        });

        addRule("<naredba>", List.of(
                "<naredba_petlje>"
        ), (node, checker, scope) -> {
            Node naredba_petlje = (Node) node.getChild(0);

            checker.check(naredba_petlje);
        });

        addRule("<naredba>", List.of(
                "<naredba_skoka>"
        ), (node, checker, scope) -> {
            Node naredba_skoka = (Node) node.getChild(0);

            checker.check(naredba_skoka);
        });


        // <izraz_naredba>

        //TODO nastavi


        // <naredba_grananja>

        //TODO nastavi


        // <naredba_petlje>

        //TODO nastavi


        // <naredba_petlje>

        //TODO nastavi

        // <naredba_skoka>

        //TODO nastavi

        addRule("<naredba_skoka>", List.of(
                "KR_RETURN",
                "<izraz>",
                "TOCKAZAREZ"
        ), (node, checker, scope) -> {
            Node izraz = (Node) node.getChild(1);

            checker.check(izraz);

            Function currentFunction = scope.getCurrentFunction();

            if(!((DataType) izraz.getProperty("tip")).implicitlyCastableTo(currentFunction.getReturnType())){
                throw new SemanticException();
            }
        });


        // <prijevodna_jedinica>

        addRule("<prijevodna_jedinica>", List.of(
                "<vanjska_deklaracija>"
        ), (node, checker, scope) -> {
            Node vanjska_deklaracija = (Node) node.getChild(0);

            checker.check(vanjska_deklaracija);
        });

        //TODO nastavi



        // <vanjska_deklaracija>

        addRule("<vanjska_deklaracija>", List.of(
                "<definicija_funkcije>"
        ), (node, checker, scope) -> {
            Node definicija_funkcije = (Node) node.getChild(0);

            checker.check(definicija_funkcije);
        });

        addRule("<vanjska_deklaracija>", List.of(
                "<deklaracija>"
        ), (node, checker, scope) -> {
            Node deklaracija = (Node) node.getChild(0);

            checker.check(deklaracija);
        });



        // <definicija_funkcije>

        addRule("<definicija_funkcije>", List.of(
                "<ime_tipa>",
                "IDN",
                "L_ZAGRADA",
                "KR_VOID",
                "D_ZAGRADA",
                "<slozena_naredba>"
        ), (node, checker, scope) -> {
            Node ime_tipa = (Node) node.getChild(0);
            Leaf IDN = (Leaf) node.getChild(1);
            Node slozena_naredba = (Node) node.getChild(5);

            checker.check(ime_tipa);

            //TODO provjeri ovo nema smisla
            if(NumericType.isConst((DataType) ime_tipa.getProperty("tip"))){
                throw new SemanticException();
            }

            if(scope.getDefinedFunction(IDN.getSourceText()) != null){
                throw new SemanticException();
            }

            FunctionType functionType = new FunctionType(VOID, (DataType) ime_tipa.getProperty("tip"));
            Function declaredFunction = scope.getDeclaredFunction(IDN.getName());
            if(declaredFunction != null){
                if(!declaredFunction.getFunctionType().equals(functionType)){
                    throw new SemanticException();
                }
            }

            scope.startFunctionDeclaration(new Function(IDN.getSourceText(), functionType));

            checker.check(slozena_naredba);

            scope.endFunctionDeclaration();
        });

        //TODO nastavi


        // <lista_parametara>

        //TODO nastavi


        // <deklaracija_parametra>

        //TODO nastavi


        // <lista_deklaracija>

        //TODO nastavi


        // <deklaracija>

        //TODO nastavi


        // <lista_init_deklaratora>

        //TODO nastavi


        // <init_deklarator>

        //TODO nastavi


        // <izravni_deklarator>

        //TODO nastavi


        // <inicijalizator>

        //TODO nastavi

        // <lista_izraza_pridruzivanja>

        //TODO nastavi
    }

}
