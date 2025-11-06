#include <bits/stdc++.h>
using namespace std;

bool subsetSum(vector<int>& arr, int target) {
    int n = arr.size();
    vector<vector<bool>> dp(n + 1, vector<bool>(target + 1, false));
    
    for (int i = 0; i <= n; i++) dp[i][0] = true;
    
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= target; j++) {
            if (arr[i - 1] > j)
                dp[i][j] = dp[i - 1][j];
            else
                dp[i][j] = dp[i - 1][j] || dp[i - 1][j - arr[i - 1]];
        }
    }
    return dp[n][target];
}

int main() {
    vector<int> arr = {3, 2, 7, 1};
    int target = 6;
    if (subsetSum(arr, target))
        cout << "Subset with sum " << target << " exists.";
    else
        cout << "No subset with sum " << target << " exists.";
    return 0;
}
