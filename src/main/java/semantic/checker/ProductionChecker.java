package semantic.checker;

import semantic.SemanticException;
import semantic.SemanticFinishedException;
import semantic.scope.ScopeController;
import semantic.tree.Node;
import semantic.tree.TreeElement;

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
            throw new SemanticFinishedException(node.toString());
        }
    }


    public void checkRoot(Node root) {

        try{
            check(root);

            //TODO one dve provjere nakon obilaska stabla
        } catch (SemanticFinishedException ex){
            out.println(ex.getMessage());
        }
    }
}
