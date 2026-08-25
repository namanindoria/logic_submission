class Solution {
    public String foreignDictionary(String[] words) {

        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            adj.add(new ArrayList<>());
        }

        // Kaunse characters actually present hain
        boolean present[] = new boolean[26];
        int total = 0;

        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                int ch = word.charAt(i) - 'a';

                if (!present[ch]) {
                    present[ch] = true;
                    total++;
                }
            }
        }

        // Adjacent words compare karo
        for (int i = 0; i < words.length - 1; i++) {

            String s1 = words[i];
            String s2 = words[i + 1];

            int l = Math.min(s1.length(), s2.length());

            int j = 0;

            while (j < l) {

                if (s1.charAt(j) != s2.charAt(j)) {

                    int n1 = s1.charAt(j) - 'a';
                    int n2 = s2.charAt(j) - 'a';

                    adj.get(n1).add(n2);

                    // First different character hi enough hai
                    break;
                }

                j++;
            }

            // Invalid case: "abc", "ab"
            if (j == l && s1.length() > s2.length()) {
                return "";
            }
        }

        ArrayList<Integer> res = topoSort(adj, present);

        // Cycle present
        if (res.size() != total) {
            return "";
        }

        String ans = "";

        for (int node : res) {
            ans += (char) (node + 'a');
        }

        return ans;
    }

    public ArrayList<Integer> topoSort(
            ArrayList<ArrayList<Integer>> adj,
            boolean present[]) {

        int V = 26;

        int indegree[] = new int[V];

        for (int i = 0; i < V; i++) {
            for (int node : adj.get(i)) {
                indegree[node]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < V; i++) {
            if (present[i] && indegree[i] == 0) {
                q.add(i);
            }
        }

        ArrayList<Integer> ans = new ArrayList<>();

        while (!q.isEmpty()) {

            int node = q.remove();
            ans.add(node);

            for (int neighbour : adj.get(node)) {

                indegree[neighbour]--;

                if (indegree[neighbour] == 0) {
                    q.add(neighbour);
                }
            }
        }

        return ans;
    }
}