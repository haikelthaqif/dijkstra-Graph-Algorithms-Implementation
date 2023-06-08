

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Ass2226200553917 {

    static int N = 1000;
    static HashMap<Integer, double[]> pubsLonLat = new HashMap <Integer, double[]>();
    static TreeMap<Integer,String> pubNames = new TreeMap<Integer,String>();
    static ArrayList<Integer> HighestNumberOfSP = new ArrayList<Integer>();
    static double[][] edgesDistance = new double[N][N];
    static Set<Integer> pubsKeys = pubNames.keySet();
    static double[][] edges = new double[N][N];


    /* Record the Starting Time */
    static long startRunTime  = System.nanoTime();
    static long endRunTime;
    static Graph g;




    static void answerDisplaySection()
    {


        System.out.println("");
        System.out.println("Question 1: " + g.shortestPaths(gettingThePubNumberFor("24 Kitchen Drive"), gettingThePubNumberFor("Baa Bar")).size());
        System.out.println("Question 2: " + HighestNumberOfSP.get(0) + " " + HighestNumberOfSP.get(1));
        System.out.println("Question 3: " + HighestNumberOfSP.get(2));
        System.out.println("Question 4: " + firstElement(g.shortestPaths(HighestNumberOfSP.get(0), HighestNumberOfSP.get(1))).size());
        System.out.println("Question 5: " + findTheFurthestPub(gettingThePubNumberFor("Cavern Pub")));
        System.out.println("Question 6: " + findingTheLengthInSum(gettingThePubNumberFor("Clock"), gettingThePubNumberFor("Elm House")));
        System.out.println("Question 7: " + findingTheLengthInKM(gettingThePubNumberFor("Foghertys"), gettingThePubNumberFor("Jolly Miller")));
        System.out.println("");

        /**** Testing purposes ******/
    }




    /**** For question 1 ******/
    static int gettingThePubNumberFor(String pubName)
    {
        int key = 0;
        for(Map.Entry<Integer, String> entry: pubNames.entrySet())
        {
            if(entry.getValue().equals(pubName))
            {
                key = entry.getKey();
            }
        }
        return key;
    }





    /**** For Question 2 and 3 ******/
    //Which pair of pubs have the highest number of shortest paths between them?
    //Calculating the maximum number of shortest path between two locations
    static void findingTheHighestNumberOfSP()
    {
        int pubStartingPositionWithHighestSP = 0;
        int pubEndPositionWithHighestSP = 0;
        int maximumNumberOfShortestPaths = 0;

        HashSet <ArrayList <Integer>> shortestPaths;

        for(int startingPub: pubsKeys)
        {
            for(int endingPub: pubsKeys)
            {
                shortestPaths = g.shortestPaths(startingPub, endingPub); // comparing the starting pub and end pub
                int currentMaxSize;

                if(!shortestPaths.isEmpty())
                {
                    currentMaxSize = shortestPaths.size();

                    if(maximumNumberOfShortestPaths < currentMaxSize)
                    {
                        maximumNumberOfShortestPaths = currentMaxSize;
                        pubStartingPositionWithHighestSP = startingPub;
                        pubEndPositionWithHighestSP = endingPub;
                    }
                }
            }
        }
        HighestNumberOfSP.add(pubStartingPositionWithHighestSP);

        HighestNumberOfSP.add(pubEndPositionWithHighestSP);

        HighestNumberOfSP.add(maximumNumberOfShortestPaths); //to store for question 3

    } // end of findingHighestNumberOfSP()






    /**** For Question 4******/
    @SuppressWarnings("unchecked")
    static ArrayList<Integer> firstElement (HashSet <ArrayList <Integer>> s)
    {
        return (ArrayList<Integer>) s.toArray()[0];
    }







    /**** For Question 5 ******/
    // Finding the furthest pub away from the Cavern Pub
    static ArrayList<Integer> findTheFurthestPub(int startingPub)
    {
        int maximum = 0;
        int currentSize = 0;
        ArrayList<Integer> furthestPubList = new ArrayList<Integer> ();
        ArrayList<Integer> tempHoldingList = new ArrayList<Integer> ();
        for(int lastPub: pubsKeys)
        {
            if(startingPub != lastPub)
            {
                HashSet<ArrayList<Integer>> temporaryHash = g.shortestPaths(startingPub, lastPub);
                if(!temporaryHash.isEmpty())
                {
                    for(ArrayList<Integer> temporary: temporaryHash)
                    {
                        currentSize = temporary.size();
                        if(maximum <=  currentSize)
                        {
                            maximum = currentSize;
                            if(!tempHoldingList.contains(lastPub)) tempHoldingList.add(lastPub);
                        }
                    }
                }
            }
        }
        for(int lastPub: tempHoldingList)
        {
            if(startingPub != lastPub)
            {
                HashSet<ArrayList<Integer>> temporaryHash = g.shortestPaths(startingPub, lastPub);
                if(!temporaryHash.isEmpty())
                {
                    for(ArrayList<Integer> temporary: temporaryHash)
                    {
                        currentSize = temporary.size();
                        if(maximum ==  currentSize)
                        {
                            if(!furthestPubList.contains(lastPub)) furthestPubList.add(lastPub);
                        }
                    }
                }
            }
        }
        return furthestPubList;
    }







    /**** For Question 6 ******/
    static double findingTheLengthInSum(int startingPub, int endPub)
    {
        double length = 0.0;
        double temporaryHolder = 0.0;
        ArrayList<Integer> pathForQ6 = g.dijkstra(startingPub, endPub);

        if(!pathForQ6.isEmpty())
        {
            if(pathForQ6.size() == 2)
            {
                temporaryHolder = edges[pathForQ6.get(0)][pathForQ6.get(1)];
                edges[pathForQ6.get(0)][pathForQ6.get(1)] = 0.0;

                ArrayList<Integer> finalizedPath = g.dijkstra(startingPub, endPub);
                edges[pathForQ6.get(0)][pathForQ6.get(1)] = temporaryHolder;

                length = g.Q.get(finalizedPath.get(finalizedPath.size() - 1));
            }

            else
            {
                length = g.Q.get(pathForQ6.get(pathForQ6.size() - 1));
            }
        }
        return length;

    }//end of findingTheLengthInSum






    /**** For Question 7 ******/
    static double realDistance(double lat1, double lon1, double lat2, double lon2)
    {
        int R = 6371;
        // km (change this constant to get miles)
        double dLat = (lat2-lat1) * Math.PI / 180;
        double dLon = (lon2-lon1) * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180 )
                        * Math.cos(lat2 * Math.PI / 180 )
                        * Math.sin(dLon/2)
                        * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d;
    }






    /**** For Question 7 ******/
    static double findingTheLengthInKM(int startingPub, int endPub)
    {
        double distance = 0.0;
        double temporary = 0.0;
        double[] currentPub = null;
        double[] secondPub = null;

        edges = edgesDistance;
        g = new Graph(edges);
        ArrayList<Integer> pathforQ7 = g.dijkstra(startingPub, endPub);

        if(!pathforQ7.isEmpty())
        {
            if(pathforQ7.size() == 2)
            {
                temporary = edges[pathforQ7.get(0)][pathforQ7.get(1)];
                edges[pathforQ7.get(0)][pathforQ7.get(1)] = 0.0;

                ArrayList<Integer> pathFinal = g.dijkstra(startingPub, endPub);
                edges[pathforQ7.get(0)][pathforQ7.get(1)] = temporary;

                for(int i = 0; i < pathforQ7.size(); i++)
                {
                    if(secondPub == null)
                    {
                        secondPub = pubsLonLat.get(pathFinal.get(i));
                    }
                    else
                    {
                        currentPub = pubsLonLat.get(pathFinal.get(i));
                        distance += realDistance(currentPub[1], currentPub[0], secondPub[1], secondPub[0]);
                        secondPub = currentPub;
                    }
                }
            }

            else
            {
                for(int i = 0; i < pathforQ7.size(); i++)
                {
                    if(secondPub == null)
                    {
                        secondPub = pubsLonLat.get(pathforQ7.get(i));
                    }
                    else
                    {
                        currentPub = pubsLonLat.get(pathforQ7.get(i));
                        distance += realDistance(currentPub[1], currentPub[0], secondPub[1], secondPub[0]);
                        secondPub = currentPub;
                    }
                }
            }
        }
        return distance;
    }// end of findTheLengthInKM









    /**** From Assignment 2 ******/
    static ArrayList<String> convert (ArrayList<Integer> m)
    {
        ArrayList<String> z= new ArrayList<String>();
        for (Integer i:m)
            z.add(pubNames.get(i));
        return z;
    }



    /**** From Assignment 2 ******/
    static HashSet<ArrayList<String>> convert (HashSet<ArrayList<Integer>> paths)
    {
        HashSet <ArrayList <String>> k= new HashSet
                <ArrayList<String>>();
        for (ArrayList <Integer> p:paths)
            k.add(convert(p));
        return k;
    }






    /****************************************************************
                                Graph class
     ----------------------------------------------------------------
     ***************************************************************/
    public static class Graph
    {
        double [] [] adj;
        static HashMap<Integer, Double> Q = new HashMap<Integer, Double>();
        double sum = 0.0;


        Graph (double [] [] a)
        //constructor for Graph class
        {
            adj = new double [a.length][a.length];
            for (int i=0;i<a.length;i++)
            {
                for (int j=0;j<a.length;j++)
                {
                    adj[i][j]=a[i][j];
                }
            }
        } //end of graph constructor





        //start of neighbours
        public HashSet<Integer> neighbours(int v)
        {
            HashSet <Integer> h = new HashSet <Integer> ();
            for (int i=0;i<adj.length;i++)
                if (adj[v][i]!=0)
                    h.add(i);
            return h;
        } //end of neighbours





        // start of vertices
        public HashSet <Integer> vertices()
        {
            HashSet <Integer> h = new HashSet <Integer>();
            for (int i=0;i<adj.length;i++)
                h.add(i);
            return h;

        } //end of vertices






        //start of add to end
        @SuppressWarnings("unchecked")
        ArrayList <Integer> addToEnd (int i, ArrayList <Integer> path)
        // returns a new path with i at the end of path
        {
            ArrayList <Integer> k;
            k=(ArrayList<Integer>)path.clone();
            k.add(i);
            return k;
        } // end of add to end





        // start of  shortestPaths1
        @SuppressWarnings("unchecked")
        public HashSet <ArrayList <Integer>> shortestPaths1(HashSet<ArrayList <Integer>> sofar, HashSet <Integer> visited, int end)
        {
            HashSet <ArrayList <Integer>> more = new HashSet <ArrayList<Integer>>();
            HashSet <ArrayList <Integer>> result = new HashSet <ArrayList<Integer>>();
            HashSet <Integer> newVisited = (HashSet <Integer>) visited.clone();
            boolean done = false;
            boolean carryon = false;

            for (ArrayList <Integer> p: sofar)
            {
                for (Integer z: neighbours(p.get(p.size()-1)))
                {
                    if (!visited.contains(z))
                    {
                        carryon=true; newVisited.add(z);
                        if (z==end) {
                            done=true;
                            result.add(addToEnd(z,p));

                        }
                        else
                            more.add(addToEnd(z,p));
                    }
                }
            }
            if (done) return result; else
            if (carryon)
                return shortestPaths1(more, newVisited, end);
            else
                return new HashSet <ArrayList <Integer>>();
        } // end of shortestPaths1








        public HashSet <ArrayList <Integer>> shortestPaths( int first, int end)
        {
            HashSet <ArrayList <Integer>> sofar = new HashSet <ArrayList<Integer>>();
            HashSet <Integer> visited = new HashSet<Integer>();
            ArrayList <Integer> starting = new ArrayList<Integer>();

            starting.add(first);
            sofar.add(starting);

            if (first==end) return sofar;
            visited.add(first);

            return shortestPaths1(sofar,visited,end);
        } //end of shortestPaths





        // start of findSmallest
        int findSmallest(HashMap<Integer,Double> t)
        {
            Object [] things= t.keySet().toArray();
            double val = t.get(things[0]);
            int least=(int) things[0];
            Set<Integer> k = t.keySet();
            for (Integer i : k)
            {
                if (t.get(i) < val)
                {
                    least=i;
                    val=t.get(i);
                }
            }
            return least;
        } //end of findSmallest




        // start of dijkstra
        @SuppressWarnings("unchecked")
        public ArrayList <Integer> dijkstra (int start, int end)
        {
            int N= adj.length;
            final double INFINITY = Double.POSITIVE_INFINITY;


            ArrayList <Integer> [] paths = new ArrayList [N];  // Path
            for (int i=0;i<N;i++)
            {
                Q.put(i, INFINITY);
                paths[i]=new ArrayList <Integer>();
                paths[i].add(Integer.valueOf(start));
            }
            HashSet <Integer> S= new HashSet<>();
            S.add(start);
            Q.put(start, 0.0);
            while (!Q.isEmpty())
            {
                int v = findSmallest(Q);
                if ((v==end) && (Q.get(v) != INFINITY))
                    return paths[end];

                double w = Q.get(v);
                S.add(v);
                for(int u: neighbours(v))
                    if (!S.contains(u))
                    {
                        double w1= w + adj[v][u];
                        if (w1 < Q.get(u))
                        {
                            Q.put(u,w1);
                            paths[u]= addToEnd(u, paths[v]);
                        }
                    }
                Q.remove(v);
            }
            return new ArrayList <Integer> ();
        } //end of dijkstra


    } //end of graph





    //start of main
    public static void main(String[] args) throws FileNotFoundException {
        /* Record the Starting Time */
        //startRunTime = System.nanoTime();

        for(int i=0;i<N;i++)
            for(int j=0;j<N;j++)
                edges[i][j]=0.0;

        /****************************************
         Initializing and declaring of File Names
         -----------------------------------------
         *****************************************/
        String firstFile = args[0]; //pubs
        String secondFile = args[1]; //pubs_lon_lat
        String thirdFile = args[2]; //randomGraph
        String z;
        // this program assumes the user runs the code with the following file arguments in this order: pubs pubs_lon_lat randomGraph


        /* Scanning the Pub Names */
        Scanner s = new Scanner(new FileReader(firstFile));
        z = s.nextLine();
        while(s.hasNext())
        {
            z = s.nextLine();
            String[] results = z.split(",");
            pubNames.put(Integer.parseInt(results[0]), results[1]);//code, ATA
        }


        /* Scanning the pub longitude and latitude*/
        s = new Scanner(new FileReader(secondFile));
        z =s.nextLine();
        while(s.hasNext())
        {
            z = s.nextLine();
            String[] results = z.split(",");
            double[] lon_lat = new double [2];
            lon_lat[0] = Double.parseDouble(results[1]);//longtitude
            lon_lat[1] = Double.parseDouble(results[2]);//latitude
            pubsLonLat.put(Integer.parseInt(results[0]), lon_lat);
        }



        /* Scanning the randomGraph file*/
        s = new Scanner(new FileReader(thirdFile));
        z =s.nextLine();
        while(s.hasNext())
        {
            z = s.nextLine();
            String[] results = z.split(",");
            edges[Integer.parseInt(results[0])][Integer.parseInt(results[1])] = Double.parseDouble(results[2]); //code, code, weight
            edgesDistance[Integer.parseInt(results[0])][Integer.parseInt(results[1])] = realDistance(
                    pubsLonLat.get(Integer.parseInt(results[0]))[1], pubsLonLat.get(Integer.parseInt(results[0]))[0],
                    pubsLonLat.get(Integer.parseInt(results[1]))[1], pubsLonLat.get(Integer.parseInt(results[1]))[0]);
        }


        g = new Graph(edges);
        System.out.println("Program calculating and generating answers for Qn 1-7 please wait");
        System.out.println(" ");

        findingTheHighestNumberOfSP();
        //this will display the answers
        answerDisplaySection();

        endRunTime = System.nanoTime();
        System.out.println("Execution Time: " + (endRunTime - startRunTime)/1000000 + " milliseconds");

    } //end of main()



} //end of program
