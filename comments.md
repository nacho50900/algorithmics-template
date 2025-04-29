Generally good, some small issues:
* Complexity of calendar not analyzed. It has interesting implications since the base case is not O(1) as the theory implies.
* No heuristic at greedy, so less efficient. Could order by those with most connections first to minimize colors. There is a comparison _if (solution[node] == None):_, but they can never be None, I think.
* For backtracking, given the measurements, it could use some in-between to see if it actually evolves according to the complexity.

Keep up the good work.
