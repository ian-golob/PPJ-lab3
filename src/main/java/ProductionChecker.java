import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductionChecker {

    private final ScopeController scope;

    private final Map<String, Map<List<String>, Rule>> rules = new HashMap<>();

    private final PrintStream out;


    public ProductionChecker(ScopeController scopeController, PrintStream output) {
        this.out = output;
        this.scope = scopeController;

        RuleLoader rl = new RuleLoader(rules);
        rl.loadRules();
    }

    private Rule getRule(String nonTerminalSymbol, List<String> rightSideSymbols){
        if(rules.get(nonTerminalSymbol) == null ||
                rules.get(nonTerminalSymbol).get(rightSideSymbols) == null){
            throw new IllegalArgumentException("Production not found for:" + nonTerminalSymbol + " -> " + rightSideSymbols);
        }

        return rules.get(nonTerminalSymbol).get(rightSideSymbols);
    }

    public void check(Node node){

        List<String> childrenNames = node.getChildren().stream().map(TreeElement::getName).collect(Collectors.toList());
        Rule rule = getRule(node.getName(), childrenNames);

        try{

         rule.check(node, this, scope);

        } catch(SemanticException ex){
            throw new SemanticFinishedException(node.getProductionErrorString());
        }
    }


    public void checkRoot(Node root) {

        try{
            check(root);

            // provjera main
            try {
                FunctionType mainType = new FunctionType(NumericType.INT, DataType.VOID);
                if(!(scope.functionIsDefined("main") &&
                        scope.getGloballyDeclaredFunction("main")
                                .getFunctionType().equals(mainType))){
                    throw new SemanticException();
                }
            } catch (SemanticException e) {
                throw new SemanticFinishedException("main");
            }

            // provjera funkcija
            try {
                for(Function function: scope.getFunctionHistory()){
                    if(!function.isDefined()){
                        throw new SemanticException();
                    }
                }
            } catch (SemanticException e) {
                throw new SemanticFinishedException("funkcija");
            }


        } catch (SemanticFinishedException ex){
            out.println(ex.getMessage());
        }
    }
}
