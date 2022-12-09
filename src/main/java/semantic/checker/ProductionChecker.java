package semantic.checker;

import semantic.scope.ScopeController;
import semantic.tree.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionChecker {

    private final ScopeController scope;

    private final Map<String, Map<List<String>, Rule>> rules = new HashMap<>();


    public ProductionChecker(ScopeController scopeController) {
        this.scope = scopeController;
    }

    public void addRule(String nonTerminalSymbol, List<String> rightSideSymbols, Rule rule){
        Map<List<String>, Rule> symbolRules = rules.getOrDefault(nonTerminalSymbol, new HashMap<>());

        symbolRules.put(rightSideSymbols, rule);

        rules.put(nonTerminalSymbol, symbolRules);
    }

    private Rule getRule(String nonTerminalSymbol, List<String> rightSideSymbols){
        return rules.get(nonTerminalSymbol).get(rightSideSymbols);
    }

    public void check(Node node){
        /*
        String nonTerminalSymbol = ...
        List<String> rightSideSymbols = ...

        Rule rule = getRule(nonTerminalSymbol, rightSideSymbols);

        try{

         rule.check(node, this, scope);
        } catch(SemanticException ex){
            out.println(node);
            System.exit(1);
        }
         */
    }


}
