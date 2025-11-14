class Solution {
  public:
    int minPlatform(vector<int>& arr, vector<int>& dep) {
        int n = arr.size();
        vector<pair<int, int>> trains;
        for (int i = 0; i < n; ++i) {
            trains.push_back({arr[i], dep[i]});
        }
        sort(trains.begin(), trains.end());
        priority_queue<int, vector<int>, greater<int>> pq;
        int maxi = 0;
        for (auto& train : trains) {
            int arrival = train.first;
            int departure = train.second;
            while (!pq.empty() && pq.top() < arrival) {
                pq.pop();
            }
            pq.push(departure);
            maxi = max(maxi, (int)pq.size());
        }
        return maxi;
    }
};