#include <bits/stdc++.h>
using namespace std;

long long closestSubSum(vector<int>& a, long long k){
    int n = a.size();
    vector<long long> pre(n+1);
    for(int i=0;i<n;i++) pre[i+1]=pre[i]+a[i];

    long long low=0, high=pre[n], ans=LLONG_MAX;

    while(low<=high){
        long long mid = (low+high)/2;

        long long best = LLONG_MAX;
        int l=0;
        for(int r=1;r<=n;r++){
            while(l<r && pre[r]-pre[l] > mid) l++;
            if(l<r) best = min(best, llabs((pre[r]-pre[l]) - k));
            if(l>0) best = min(best, llabs((pre[r]-pre[l+1]) - k));
        }

        ans = min(ans, best);

        if(mid < k) low = mid + 1;
        else high = mid - 1;
    }

    return k + (ans == LLONG_MAX ? 0 : (ans==0?0:(ans>0? (ans) : (-ans))));
}

int main(){
    vector<int> a = {1,2,3,4,5};
    long long k = 11;
    cout << closestSubSum(a,k);
}
