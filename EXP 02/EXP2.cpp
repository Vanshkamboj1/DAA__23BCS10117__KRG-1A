class Solution {
public:
    double myPow(double a, int n) {
        long long exp = n;
        if (exp < 0) {
            exp = -exp;
            a = 1 / a;
        }
        double res = 1.0;
        while (exp > 0) {
            if (exp & 1) res *= a;
            a *= a;
            exp >>= 1;
        }
        return res;
    }
};
