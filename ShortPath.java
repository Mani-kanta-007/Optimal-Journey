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
    int[][] adj ; // Cost
    int[][] time;  // time
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
    // Changes done to Method not completed Successfully - > one time run successed
    Pair minCostToDest(String source,String dest){
        int[] path_cost = new int[n+1];
        int[] time_to_reach_dest = new int[n+1];

        for(int i=0;i<=n;i++){ 
            path_cost[i] = (int)(1e9);
            time_to_reach_dest[i] = (int)(1e9);
        }
        int src_idx = Arrays.asList(city).indexOf(source) , dst_idx = Arrays.asList(city).indexOf(dest);

        PriorityQueue<Pair> pq = new PriorityQueue<>((a,b)->a.cost - b.cost);
        int cost = 0;
        int timer=0;

        path_cost[src_idx] = cost;
        time_to_reach_dest[src_idx] = timer;
        pq.add(new Pair(src_idx,cost,timer));

        while(!pq.isEmpty()){
            Pair pop = pq.poll();
            int internal_city_idx = pop.city_idx;
            for(int i=0;i<=n;i++){
                if(adj[internal_city_idx][i]!=-1 && (adj[internal_city_idx][i]+ pop.cost < path_cost[i])){
                    int cur_cost = pop.cost + adj[internal_city_idx][i];
                    int cur_time = pop.time + time[internal_city_idx][i];
                    pq.add(new Pair(i, cur_cost,cur_time));
                    time_to_reach_dest[i] = cur_time;
                    path_cost[i] =  cur_cost;
                }
            }
        }
        Pair ans = new Pair(dst_idx, path_cost[dst_idx],time_to_reach_dest[dst_idx]);
        return ans;
    }
    // Changes done to Method not completed Successfully - > one time run successed
    Pair minTimeToDest(String source,String dest){
        int[] time_to_reach_dest = new int[n+1];
        int[] cost_to_reach_dest = new int[n+1];
        for(int i=0;i<=n;i++){
            time_to_reach_dest[i] = (int)(1e9);
            cost_to_reach_dest[i] = (int)(1e9);
        }
        int src_idx = Arrays.asList(city).indexOf(source) , dst_idx = Arrays.asList(city).indexOf(dest);

        PriorityQueue<Pair> pq = new PriorityQueue<>((a,b)->a.time - b.time);
        int timer = 0;
        int cost = 0;

        time_to_reach_dest[src_idx] = timer;
        cost_to_reach_dest[src_idx] = cost;

        pq.add(new Pair(src_idx, cost,timer));

        while(!pq.isEmpty()){
            Pair pop = pq.poll();
            int internal_city_idx = pop.city_idx;

            for(int i=0;i<=n;i++){
                if(time[internal_city_idx][i]!=-1 && (time[internal_city_idx][i]+ pop.time < time_to_reach_dest[i])){
                    int cur_time = pop.time + time[internal_city_idx][i];
                    int cur_cost = pop.cost + adj[internal_city_idx][i];
                    pq.add(new Pair(i, cur_cost,cur_time));
                    time_to_reach_dest[i] =  cur_time;
                    cost_to_reach_dest[i] = cur_cost;
                }
            }

        }
        Pair ans = new Pair(dst_idx, cost_to_reach_dest[dst_idx],time_to_reach_dest[dst_idx]);
        return ans;
    }




    // Not Coded Completely
    Pair decreaseCost(int min_cost,int max_cost,String source,String dest){
        //Write Code
        int src_idx = Arrays.asList(city).indexOf(source);
        int dest_idx = Arrays.asList(city).indexOf(dest);
        int[] time_to_reach_dest = new int[n+1];
        int[] cost_to_reach_dest = new int[n+1];
        for(int i=0;i<=n;i++){
            time_to_reach_dest[i] = (int)(1e9);
            cost_to_reach_dest[i] = (int)(1e9);
        }
        Queue<Pair> que = new LinkedList<Pair>();
        int timer = 0;
        int cost = 0;
        time_to_reach_dest[src_idx] = timer;
        cost_to_reach_dest[src_idx] = cost;

        que.add(new Pair(src_idx,cost,timer)); // for Looping
        while(!que.isEmpty()){
            Pair pop = que.remove();
            for(int i=0;i<adj[0].length;i++){
                int temp_cost = adj[pop.city_idx][i]+ pop.cost;
                if((adj[pop.city_idx][i]!=-1 && (temp_cost < cost_to_reach_dest[i]) && (temp_cost< max_cost) ) && temp_cost != min_cost){
                    que.add(new Pair(i, pop.cost+adj[pop.city_idx][i], pop.time+time[pop.city_idx][i]));
                    cost_to_reach_dest[i] = adj[pop.city_idx][i]+ pop.cost;
                    time_to_reach_dest[i] = time[pop.city_idx][i]+ pop.time;
                }
            }
        }

        return new Pair(dest_idx,cost_to_reach_dest[dest_idx],time_to_reach_dest[dest_idx]);
    }

    ArrayList<String> decreaseCostPath(int min_cost,int max_cost,String source,String dest){
        int[] path_cost = new int[n+1];
        int[] parent = new int[n+1]; // Used for finding the Path
        boolean[] visited_city = new boolean[n+1];
        for(int i=0;i<=n;i++) parent[i] = i;
        for(int i=0;i<=n;i++) path_cost[i] = (int)(1e9);
        int src_idx = Arrays.asList(city).indexOf(source) , dst_idx = Arrays.asList(city).indexOf(dest);
        Queue<Pair> que = new LinkedList<Pair>();
        visited_city[src_idx] = true;
        que.add(new Pair(src_idx,0,0)); // for Looping
        while(!que.isEmpty()){
            Pair pop = que.remove();
            for(int i=0;i<adj[0].length;i++){
                int temp_cost = adj[pop.city_idx][i]+ pop.cost;
                if((adj[pop.city_idx][i]!=-1 && (temp_cost < path_cost[i]) && (temp_cost< max_cost) ) && temp_cost != min_cost && !visited_city[i]){
                    visited_city[i] = true;
                    que.add(new Pair(i, pop.cost+adj[pop.city_idx][i], pop.time+time[pop.city_idx][i]));
                    path_cost[i] = adj[pop.city_idx][i]+ pop.cost;
                    parent[i] = pop.city_idx;
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






    //MinCostPath
    ArrayList<String> minCostPath(String source,String dest){
        int[] path_cost = new int[n+1];
        int[] parent = new int[n+1]; // Used for finding the Path
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
class ShortPath{
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


        String source , dest;
        
        System.out.print("Enter Starting City : "); source = sc.nextLine();
        System.out.print("Enter Destination City : "); dest = sc.nextLine();
        System.out.println();
        System.out.println("<---------------------------------------------------------------------->");
        System.out.println();
        Pair ans = map.minCostToDest(source,dest);
        System.out.println("Minimum Cost from "+source+" to "+dest+" : $"+ans.cost);
        System.out.println();
        System.out.println("Time Of the Journet : "+ans.time+"hrs"); // Min Time
        System.out.println();
        System.out.println("Minimum Cost Path from "+source+" to "+dest+" : ");
        ArrayList<String> path = map.minCostPath(source,dest);
        System.out.println();
        Collections.reverse(path);
        for(String cities : path) System.out.print(cities+"-->");
        System.out.print(dest);
        System.out.println();
        System.out.println("\n<---------------------------------------------------------------------->");
        System.out.println();
        Pair ans1 = map.minTimeToDest(source,dest);
        System.out.print("Minimum Time from "+source+" to "+dest+" : "+ans1.time+"hrs\n");
        System.out.println();
        System.out.println("Cost of the Journey : $"+ans1.cost); // Max Cost
        System.out.println();
        System.out.println("Minimum Time Path from "+source+" to "+dest+" : ");
        ArrayList<String> path2 = map.minTimePath(source,dest);
        System.out.println();
        Collections.reverse(path2);
        for(String cities : path2) System.out.print(cities+"-->");
        System.out.print(dest);
        System.out.println();
        System.out.println("\n<---------------------------------------------------------------------->");
        System.out.println();
        Pair ans2 = map.decreaseCost(ans.cost,ans1.cost, source, dest);
        if(ans2.cost==(int)(1e9) || ans2.time==(int)(1e9)){
            System.out.println("No Alternate Path");
            sc.close();
            return ;
        }
        System.out.println("Alternate Path Balancing Both Time And Cost");
        System.out.println();
        System.out.println("Decresed Cost : $"+ans2.cost);
        System.out.println();
        System.out.println("Decresed Time : "+ans2.time+"hrs");
        System.out.println();
        ArrayList<String> path3 = map.decreaseCostPath(ans.cost,ans1.cost,source,dest);
        System.out.println();
        Collections.reverse(path3);
        for(String cities : path3) System.out.print(cities+"-->");
        System.out.print(dest);
        System.out.println();
        System.out.println("\n<---------------------------------------------------------------------->");
        sc.close();
    }
}
// Consider source = Chicago And Destination = Miami for better Output