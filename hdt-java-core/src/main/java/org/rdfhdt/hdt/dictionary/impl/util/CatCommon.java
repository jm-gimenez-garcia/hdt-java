package org.rdfhdt.hdt.dictionary.impl.util;

import org.rdfhdt.hdt.util.string.CharSequenceComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.math3.util.Pair;


/**
 * @author alyhdr
 *
 */
public class CatCommon implements Iterator<IterElement> {
    CharSequenceComparator comparator = new CharSequenceComparator();
    ArrayList<IteratorPlusString> list;
    private Iterator<? extends CharSequence> it1;
    private Iterator<? extends CharSequence> it2;
    
    private String firstIterName = "DEFAULT";
    private String secondIterName = "DEFAULT";
    
    int count1 = 0;
    int count2 = 0;
    boolean hasNext = false;
    IterElement next;

    public CatCommon(Iterator<? extends CharSequence> it1, Iterator<? extends CharSequence> it2,String firstIterName,String secondIterName) {
        this.it1 = it1;
        this.it2 = it2;
        this.firstIterName = firstIterName;
        this.secondIterName = secondIterName;
        list = new ArrayList<IteratorPlusString>();
        if (it1.hasNext()) {
            list.add(new IteratorPlusString(1, it1.next()));
        }
        if (it2.hasNext()) {
            list.add(new IteratorPlusString(2, it2.next()));
        }
        helpNext();
    }
    public CatCommon(Iterator<? extends CharSequence> it1, Iterator<? extends CharSequence> it2) {
        this.it1 = it1;
        this.it2 = it2;
        list = new ArrayList<IteratorPlusString>();
        if (it1.hasNext()) {
            list.add(new IteratorPlusString(1, it1.next()));
        }
        if (it2.hasNext()) {
            list.add(new IteratorPlusString(2, it2.next()));
        }
        helpNext();
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public IterElement next() {
        IterElement r = next;
        hasNext=false;
        helpNext();
        return r;
    }

    private void helpNext(){
        while (list.size() != 0) {
//      while(list.size()!=0) {
//            for (IteratorPlusString s : list){
//                System.out.println(s.iterator+" --- "+s.value);
//            }
//            System.out.println("");
            Collections.sort(list, new ScoreComparator());
            if (list.size() == 2) {
                if (list.get(0).value.toString().equals(list.get(1).value.toString())) {
                    hasNext = true;
                    next = new IterElement(new Pair<Integer, Integer>(count1, count2),list.get(0).value.toString(),this.firstIterName,this.secondIterName);
                    boolean remove = false;
                    if (it1.hasNext()) {
                        list.set(0, new IteratorPlusString(1, it1.next()));
                        count1++;
                    } else {
                        list.remove(0);
                        remove = true;
                    }
                    if (it2.hasNext()) {
                        count2++;
                        if (remove==true){
                            list.set(0, new IteratorPlusString(2, it2.next()));
                        } else {
                            list.set(1, new IteratorPlusString(2, it2.next()));
                        }

                    } else {
                        list.remove(0);
                    }
                    break;
                } else {
                    if (list.get(0).iterator == 1) {
                        if (it1.hasNext()) {
                            list.set(0, new IteratorPlusString(1, it1.next()));
                            count1++;
                        } else {
                            list.remove(0);
                        }
                    } else {
                        if (it2.hasNext()) {
                            count2++;
                            list.set(0, new IteratorPlusString(2, it2.next()));
                        } else {
                            list.remove(0);
                        }
                    }
                }
            } else if (list.size() == 1) {
                list.remove(0);
            }
        }
    }
    @Override
    public void remove() {
        try {
            throw new Exception("Not implemented");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}