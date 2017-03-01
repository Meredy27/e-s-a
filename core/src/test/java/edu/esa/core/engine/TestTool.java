package edu.esa.core.engine;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TestTool {
    public boolean hasChain(Collection<List<String>> all, List<String> chain) {
        for(List<String> ch : all) {
            if(checkChainsEqual(ch, chain)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkChainsEqual(List<String> chain1, List<String> chain2) {
        if(chain1.size() != chain2.size()) {
            return false;
        }
        Iterator<String> it1 = chain1.iterator();
        Iterator<String> it2 = chain2.iterator();

        while(it1.hasNext()) {
            if(!it1.next().equals(it2.next())) {
                return false;
            }
        }

        return true;
    }

    public boolean hasChainForCycle(Collection<List<String>> all, List<String> chain) {
        for(List<String> ch : all) {
            if(checkChainsEqualForCycle(ch, chain)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkChainsEqualForCycle(List<String> chain1, List<String> chain2) {
        if(chain1.size() != chain2.size()) {
            return false;
        }

        String element = chain1.get(0);

        int tab = -1;
        for(int i = 0; i< chain2.size(); i++) {
            if(element.equals(chain2.get(i))) {
                tab = i;
                break;
            }
        }

        if(tab < 0) {
            return false;
        }

        for(int i=0; i < chain1.size() - tab; i++) {
            if (!chain1.get(i).equals(chain2.get(i + tab))) {
                return false;
            }
        }

        for(int i=0; i < tab; i++) {
            if (!chain2.get(i).equals(chain1.get(chain1.size() - tab + i))) {
                return false;
            }
        }

        return true;
    }
}
