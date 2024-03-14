import java.util.*;
class Pair{
    int city_idx;
    int cost;
    int time;
    Pair(int city_idx,int cost){
        this.city_idx = city_idx;
        this.cost = cost;
    }
    Pair(int city_idx,int cost,int time){
        this.city_idx = city_idx;
        this.cost = cost;
        this.time = time;
    }
}
class Graph{
    int[][] adj ;
    int[][] time;
    String[] city;
    int n ;
    Graph(int n){
        this.n=n;
        adj = new int[n+1][n+1];
        time = new int[n+1][n+1];
        for(int i=0;i<=n;i++){
            for(int j=0;j<=n;j++){
                adj[i][j] = -1; // -1 -> No Path Exist. >0 -> exist path Cost
                time[i][j] = -1;
            }
        } 
    }
    void addEdge(String source,String dest,int cost,int time_to_reach_dest){
        int source_idx = Arrays.asList(city).indexOf(source);
        int dest_idx = Arrays.asList(city).indexOf(dest);
        adj[source_idx][dest_idx] = cost;
        adj[dest_idx][source_idx] = cost;
        time[source_idx][dest_idx] = time_to_reach_dest;
        time[dest_idx][source_idx] = time_to_reach_dest;
    }
    int minCostToDest(String source,String dest){
        int[] path_cost = new int[n+1];
        for(int i=0;i<=n;i++) path_cost[i] = (int)(1e9);
        int src_idx = Arrays.asList(city).indexOf(source) , dst_idx = Arrays.asList(city).indexOf(dest);
        PriorityQueue<Pair> pq = new PriorityQueue<>((a,b)->a.cost - b.cost);
        int cost = 0;
        path_cost[src_idx] = cost;
        pq.add(new Pair(src_idx, cost));
        while(!pq.isEmpty()){
            Pair pop = pq.poll();
            int internal_city_idx = pop.city_idx;
            for(int i=0;i<=n;i++){
                if(adj[internal_city_idx][i]!=-1 && (adj[internal_city_idx][i]+ pop.cost < path_cost[i])){
                    int cur_cost = pop.cost + adj[internal_city_idx][i];
                    pq.add(new Pair(i, cur_cost));
                    path_cost[i] =  cur_cost;
                }
            }
        }
        return path_cost[dst_idx];
    }
    int minTimeToDest(String source,String dest){
        int[] time_to_reach_dest = new int[n+1];
        for(int i=0;i<=n;i++) time_to_reach_dest[i] = (int)(1e9);
        int src_idx = Arrays.asList(city).indexOf(source) , dst_idx = Arrays.asList(city).indexOf(dest);
        PriorityQueue<Pair> pq = new PriorityQueue<>((a,b)->a.time - b.time);
        int timer = 0;
        int cost = 0;
        time_to_reach_dest[src_idx] = timer;
        pq.add(new Pair(src_idx, cost,timer));
        while(!pq.isEmpty()){
            Pair pop = pq.poll();
            int internal_city_idx = pop.city_idx;
            for(int i=0;i<=n;i++){
                if(time[internal_city_idx][i]!=-1 && (time[internal_city_idx][i]+ pop.time < time_to_reach_dest[i])){
                    int cur_time = pop.time + time[internal_city_idx][i];
                    pq.add(new Pair(i, cost,cur_time));
                    time_to_reach_dest[i] =  cur_time;
                }
            }
        }
        return time_to_reach_dest[dst_idx];
    }
    ArrayList<String> minCostPath(String source,String dest){
        int[] path_cost = new int[n+1];
        int[] parent = new int[n+1];
        for(int i=0;i<=n;i++) parent[i] = i;
        for(int i=0;i<=n;i++) path_cost[i] = (int)(1e9);
        int src_idx = Arrays.asList(city).indexOf(source) , dst_idx = Arrays.asList(city).indexOf(dest);
        PriorityQueue<Pair> pq = new PriorityQueue<>((a,b)->a.cost - b.cost);
        int cost = 0;
        path_cost[src_idx] = cost;
        pq.add(new Pair(src_idx, cost));
        while(!pq.isEmpty()){
            Pair pop = pq.poll();
            int internal_city_idx = pop.city_idx;
            for(int i=0;i<=n;i++){
                if(adj[internal_city_idx][i]!=-1 && (adj[internal_city_idx][i]+ pop.cost < path_cost[i])){
                    parent[i] = pop.city_idx;
                    int cur_cost = pop.cost + adj[internal_city_idx][i];
                    pq.add(new Pair(i, cur_cost));
                    path_cost[i] =  cur_cost;
                }
            }
        }
        ArrayList<String> path_names = new ArrayList<>();
        while(parent[dst_idx]!=dst_idx){
            path_names.add(city[parent[dst_idx]]);
            dst_idx = parent[dst_idx];
        }
        return path_names;
    }
    // MinTimePath
    ArrayList<String> minTimePath(String source,String dest){
        int[] path_time = new int[n+1];
        int[] parent = new int[n+1];
        for(int i=0;i<=n;i++) parent[i] = i;
        for(int i=0;i<=n;i++) path_time[i] = (int)(1e9);
        int src_idx = Arrays.asList(city).indexOf(source) , dst_idx = Arrays.asList(city).indexOf(dest);
        PriorityQueue<Pair> pq = new PriorityQueue<>((a,b)->a.time - b.time);
        int start_time = 0;
        path_time[src_idx] = start_time;
        pq.add(new Pair(src_idx,start_time,start_time));
        while(!pq.isEmpty()){
            Pair pop = pq.poll();
            int internal_city_idx = pop.city_idx;
            for(int i=0;i<=n;i++){
                if(time[internal_city_idx][i]!=-1 && (time[internal_city_idx][i]+ pop.time < path_time[i])){
                    parent[i] = pop.city_idx;
                    int cur_cost = pop.time + time[internal_city_idx][i];
                    pq.add(new Pair(i,start_time, cur_cost));
                    path_time[i] =  cur_cost;
                }
            }
        }
        ArrayList<String> path_names = new ArrayList<>();
        while(parent[dst_idx]!=dst_idx){
            path_names.add(city[parent[dst_idx]]);
            dst_idx = parent[dst_idx];
        }
        return path_names;
    }

}
class Short{
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        // Fixed Map
        Graph map = new Graph(15);

        // For Indexing 
        map.city = new String[]{"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Seattle", "Boston", "San Francisco", "Denver", "Miami", "Atlanta", "Dallas", "Las Vegas", "Orlando", "Portland"};

        // Adding Edges
        map.addEdge("New York", "Los Angeles", 100, 5);
        map.addEdge("New York", "Chicago", 80, 3);
        map.addEdge("Boston", "New York", 120, 4);
        map.addEdge("Chicago", "Houston", 70, 2);
        map.addEdge("Chicago", "Phoenix", 110, 4);
        map.addEdge("Los Angeles", "Chicago", 90, 4);
        map.addEdge("Los Angeles", "Houston", 80, 3);
        map.addEdge("Las Vegas", "Los Angeles", 90, 3);
        map.addEdge("Phoenix", "Houston", 90, 2);
        map.addEdge("Houston", "San Francisco", 100, 8);
        map.addEdge("Seattle", "Boston", 70, 2);
        map.addEdge("Phoenix", "San Francisco", 140, 4);
        map.addEdge("Phoenix", "Denver", 120, 3);
        map.addEdge("San Francisco", "Portland", 80, 2);
        map.addEdge("Boston", "San Francisco", 110, 8);
        map.addEdge("San Francisco", "Denver", 150, 4);
        map.addEdge("Miami", "San Francisco", 100, 6);
        map.addEdge("Dallas", "San Francisco", 80, 4);
        map.addEdge("Denver", "Portland", 300, 10);
        map.addEdge("Portland", "Boston", 280, 5);
        map.addEdge("Boston", "Miami", 200, 6);
        map.addEdge("Miami", "Atlanta", 90, 3);
        map.addEdge("Dallas", "Miami", 150, 4);
        map.addEdge("Las Vegas", "Miami", 120, 7);
        map.addEdge("Atlanta", "Dallas", 100, 3);
        map.addEdge("Dallas", "Las Vegas", 170, 4);



        // Removing Direct Paths without Considering Internal Cities
        /*map.addEdge("Seattle", "San Francisco", 90, 2);
        map.addEdge("Denver", "Seattle", 110, 3);
        map.addEdge("Seattle", "Portland", 70, 2);
        map.addEdge("Dallas", "Houston", 80, 2);
        map.addEdge("Las Vegas", "Phoenix", 80, 2);
        map.addEdge("Las Vegas", "San Francisco", 200, 5);*/

        
        
        

        String source , dest;
        
        System.out.print("Enter Starting City : "); source = sc.nextLine();
        System.out.print("Enter Destination City : "); dest = sc.nextLine();
        System.out.println("<---------------------------------------------------------------------->");
        System.out.print("Minimum Cost from "+source+" to "+dest+" : ");
        System.out.println("$"+map.minCostToDest(source,dest));
        System.out.println();
        System.out.println("Cost Path from "+source+" to "+dest+" ");
        ArrayList<String> path = map.minCostPath(source,dest);
        System.out.println();
        Collections.reverse(path);
        for(String cities : path) System.out.print(cities+"-->");
        System.out.print(dest);
        System.out.println("\n<---------------------------------------------------------------------->");
        // Take Care
        System.out.print("Minimum Time from "+source+" to "+dest+" : "+map.minTimeToDest(source,dest)+"hrs\n");
        System.out.println();
        System.out.println("Time Path from "+source+" to "+dest+" ");
        ArrayList<String> path2 = map.minTimePath(source,dest);
        System.out.println();
        Collections.reverse(path2);
        for(String cities : path2) System.out.print(cities+"-->");
        System.out.print(dest);
        System.out.println("\n<---------------------------------------------------------------------->");
        sc.close();
    }
}