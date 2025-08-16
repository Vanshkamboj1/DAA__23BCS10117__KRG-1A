class Solution {
  public:
    vector<vector<int>> countFreq(vector<int>& arr) {
        sort(arr.begin(), arr.end());
        vector<vector<int>> result;
        int n = arr.size();

        for (int i = 0; i < n;) {
            int element = arr[i];
            int count = 0;
            while (i < n && arr[i] == element) {
                count++;
                i++;
            }
            result.push_back({element, count});
        }
        return result;

    }
};