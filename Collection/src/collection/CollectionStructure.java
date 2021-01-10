package collection;

import org.junit.Test;

/**
 * @author BEYOND
 */
public class CollectionStructure {
 //Collection接口框架图
    @Test
public void interFacesStructureMap(){
        System.out.println("\n" +
                "        Collection                       Map                Iterator          RandomAccess\n" +
                "            ↑                             ↑                     ↑\n" +
                "____________↑___________                  ↑                     ↑\n" +
                "List       Set      Queue             SortedMap            ListIterator\n" +
                "            ↑         ↑                   ↑\n" +
                "        SortedSet   Deque             NavigableMap\n" +
                "            ↑\n" +
                "       NavigableSet");
}
   @Test
    public void classStructureMap(){
    System.out.println("                                        AbstractCollection\n" +
            "                                                ↑\n" +
            "                  ______________________________↑_____________________________________________\n" +
            "            AbstractList                   AbstractSet               AbstractQueue        ArrayQueue\n" +
            "                 ↑                              ↑                           ↑\n" +
            "         _______ ↑_______           ____________↑__________            PriorityQueue\n" +
            "     Abstract            ▏     HashSet      EnumSet    TreeSet\n" +
            "  SequentialList         ▏         ▏\n" +
            "        ↑                ▏         ▏\n" +
            "   LinkedList      ArrayList    LInkedHashSet\n" +
            "\n" +
            "        \n" +
            "                                          AbstractMap\n" +
            "             —————————————————————————————————↑————————————————————————————————————————\n" +
            "             ▏               ▏               ▏                  ▏                   ▏\n" +
            "           HashMap         TreeMap          EnumMap            WeekHashMap       IdentityHashMap\n" +
            "             ▏\n" +
            "        LinkedHashMap");
}
}
