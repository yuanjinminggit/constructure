package com.leetcode.codereview.greedy;

import com.leetcode.codereview.simpleConstruct.ListNode;
import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class Qiegedangao {
    public int maxArea(int h, int w, int[] ho, int[] ve) {
        int MOD = (int) 1e9 + 7;
        Arrays.sort(ho);
        Arrays.sort(ve);
        long maxH = getMax(h, ho);
        long maxW = getMax(w, ve);
        return (int) (maxH * maxW % MOD);
    }

    private long getMax(int h, int[] ho) {
        long maxH = 0;
        for (int i = 1; i < ho.length; i++) {
            maxH = Math.max(ho[i] - ho[i - 1], maxH);
        }
        maxH = Math.max(maxH, ho[0]);
        maxH = Math.max(maxH, h - ho[ho.length - 1]);
        return maxH;
    }

    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        int right = 0, ret = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1 && ratings[i] > ratings[i + 1]) {
                right++;
            } else {
                right = 1;
            }
            ret += Math.max(left[i], right);
        }
        return ret;
    }

    // 暴力解法
    public int maxPoints1(int[][] ps) {
        int n = ps.length;
        int ans = 1;
        for (int i = 0; i < n; i++) {
            int[] x = ps[i];
            for (int j = i + 1; j < n; j++) {
                int[] y = ps[j];
                int cnt = 2;
                for (int k = j + 1; k < n; k++) {
                    int[] p = ps[k];
                    int s1 = (y[1] - x[1]) * (p[0] - y[0]);
                    int s2 = (p[1] - y[1]) * (y[0] - x[0]);
                    if (s1 == s2) {
                        cnt++;
                    }
                }
                ans = Math.max(ans, cnt);
            }
        }
        return ans;
    }

    public int findGCD(int[] nums) {
        int min = 1001;
        int max = 1;
        for (int num : nums) {
            min = Math.min(num, min);
            max = Math.max(num, max);
        }
        return getGCD(max, min);
    }

    private int getGCD(int max, int min) {
        if (max < min) {
            return getGCD(min, max);
        }
        if (max % min == min) {
            return min;
        }

        return getGCD(max % min, min);
    }

    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2) {
            return n;
        }
        int ret = 0;
        for (int i = 0; i < n; i++) {
            if (ret >= n - i || ret > n / 2) {
                break;
            }
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int x = points[i][0] - points[j][0];
                int y = points[i][1] - points[j][1];
                if (x == 0) {
                    y = 1;
                } else if (y == 0) {
                    x = 1;
                } else {
                    if (y < 0) {
                        x = -x;
                        y = -y;
                    }
                    int gcdXY = gcd(Math.abs(x), Math.abs(y));
                    x /= gcdXY;
                    y /= gcdXY;
                }
                int key = y + x * 20001;
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
            int maxn = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int num = entry.getValue();
                maxn = Math.max(maxn, num + 1);
            }
            ret = Math.max(ret, maxn);
        }
        return ret;
    }

    private int gcd(int a, int b) {
        return b != 0 ? gcd(b, a % b) : a;
    }

    public int maxProduct(int[] nums) {
        int n = nums.length;
        int max = Integer.MIN_VALUE;
        mem = new int[n][2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(mem[i], Integer.MIN_VALUE / 2);
        }
        for (int i = 0; i < n; i++) {
            max = Math.max(max, dfsmaxProduct(i, nums, 1));
        }
        return max;
    }

    private int[][] mem;

    private int dfsmaxProduct(int i, int[] nums, int flag) {
        if (i == 0) {
            return nums[0];
        }
        if (mem[i][flag] != Integer.MIN_VALUE / 2) {
            return mem[i][flag];
        }
        if (nums[i] >= 0) {
            if (flag == 1) {
                return mem[i][flag] = Math.max(dfsmaxProduct(i - 1, nums, 1) * nums[i], nums[i]);
            } else {
                return mem[i][flag] = Math.min(nums[i], dfsmaxProduct(i - 1, nums, 0) * nums[i]);
            }
        } else {
            if (flag == 1) {
                return mem[i][flag] = Math.max(nums[i], dfsmaxProduct(i - 1, nums, 0));
            }
            return mem[i][flag] = Math.min(nums[i], dfsmaxProduct(i - 1, nums, 1) * nums[i]);
        }
    }

    public boolean haveConflict(String[] event1, String[] event2) {
        return event1[1].compareTo(event2[0]) < 0 || event2[1].compareTo(event1[0]) < 0;
    }

    public int maxProduct(String[] words) {
        HashSet<Character> set = new HashSet<Character>();
        int max = 0;
        for (int i = 0; i < words.length; i++) {
            set.clear();
            for (int k = 0; k < words[i].length(); k++) {
                set.add(words[i].charAt(k));
            }
            int l = words[i].length();

            for (int j = i + 1; j < words.length; j++) {
                boolean flag = false;
                for (int k = 0; k < words[j].length(); k++) {
                    if (set.contains(words[j].charAt(k))) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    max = Math.max(l * words[j].length(), max);
                }
                System.out.println(max);
            }
        }
        return max;
    }

    public List<String> findRepeatedDnaSequences(String s) {
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<String> res = new ArrayList<>();
        for (int i = 10; i < s.length(); i++) {
            String tmp = s.substring(i - 10, i);
            System.out.println(tmp.length());
            map.put(tmp, map.getOrDefault(tmp, 0) + 1);
            if (map.get(tmp) == 2) {
                res.add(tmp);
            }
        }
        return res;
    }

    public int findTheLongestBalancedSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int len = 0;
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                len++;
            } else {
                int tmpLen = 0;
                while (i < s.length() && s.charAt(i) == '1') {
                    i++;
                    tmpLen++;
                }
                maxLen = Math.max(Math.min(tmpLen, len) * 2, maxLen);
                len = 0;
                i--;
            }
        }
        return maxLen;
    }

    public int vowelStrings(String[] words, int left, int right) {
        int len = 0;
        for (int i = left; i < right; i++) {
            int length = words[i].length();
            if ((words[i].charAt(0) == 'a' || words[i].charAt(0) == 'e' || words[i].charAt(0) == 'i' || words[i].charAt(0) == 'o' || words[i].charAt(0) == 'u') && (
                    words[i].charAt(length - 1) == 'a' || words[i].charAt(length - 1) == 'e' || words[i].charAt(length - 1) == 'i' || words[i].charAt(length - 1) == 'o' || words[i].charAt(length - 1) == 'u')
            ) {
                len++;
            }
        }
        return len;
    }

    public int minDeletion(int[] nums) {
        int n = nums.length, cnt = 0;
        for (int i = 0; i < n; i++) {
            if ((i - cnt) % 2 == 0 && i + 1 < n && nums[i] == nums[i + 1]) {
                cnt++;
            }
        }
        return (n - cnt) % 2 != 0 ? cnt + 1 : cnt;
    }

    public int[] successfulPairs1(int[] spells, int[] potions, long success) {
        int[] ans = new int[spells.length];
        int cnt = 0;
        for (int i = 0; i < spells.length; i++) {
            for (int j = 0; j < potions.length; j++) {
                if (spells[i] * potions[j] >= success) {
                    cnt++;
                }
            }
            ans[i] = cnt;
            cnt = 0;
        }
        return ans;
    }

    public int[] successfulPairs2(int[] spells, int[] potions, long success) {
        Integer[] idx = new Integer[spells.length];
        for (int i = 0; i < idx.length; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (a, b) -> spells[a] - spells[b]);
        Arrays.sort(potions);
        int[] res = new int[spells.length];
        int tmp = 0;
        int preIdx = potions.length - 1;
        for (int i = 0; i < idx.length; i++) {
            for (int j = preIdx; j >= 0; j--) {
                if ((long) spells[idx[i]] * potions[j] >= success) {
                    preIdx = j - 1;
                    tmp++;
                } else {
                    break;
                }
            }
            res[idx[i]] = tmp;
        }
        return res;
    }

    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        int n = spells.length, m = potions.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            long target = (success + spells[i] - 1) / spells[i];
            if (target <= potions[potions.length - 1]) {
                spells[i] = potions.length - binarySearch(potions, target);
            } else {
                spells[i] = 0;
            }
        }
        return spells;
    }

    private int binarySearch(int[] potions, long target) {
        int l = 0;
        int r = potions.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (potions[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return r;
    }

    public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int x = queries[i][0];
            int y = queries[i][1];
            int max = getMax(nums1, nums2, x, y);
            ans[i] = max;
        }
        return ans;
    }

    private int getMax(int[] nums1, int[] nums2, int x, int y) {
        int n = nums1.length;
        int max = -1;
        for (int i = 0; i < n; i++) {
            if (nums1[i] >= x && nums2[i] >= y) {
                max = Math.max(max, nums1[i] + nums2[i]);
            }
        }
        return max;
    }

    public int maximumSum1(int[] nums) {
        HashMap<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int sum = getSum(nums[i]);
            PriorityQueue<Integer> heap = map.getOrDefault(sum, new PriorityQueue<Integer>(Comparator.reverseOrder()));
            heap.offer(nums[i]);
            map.put(sum, heap);
        }
        int max = 0;
        for (PriorityQueue<Integer> queue : map.values()) {
            if (queue.size() >= 2) {
                int t = queue.poll() + queue.poll();
                max = Math.max(t, max);
            }
        }
        return max;
    }

    private int getSum(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    public int maximumSum(int[] nums) {
        int[] val = new int[100];
        int ans = -1;
        for (int x : nums) {
            int t = x, cur = 0;
            while (t != 0) {
                cur += t % 10;
                t /= 10;
            }
            if (val[cur] != 0) ans = Math.max(ans, cur + val[cur]);
            val[cur] = Math.max(val[cur], x);
        }
        return ans;
    }

    int n;
    int[] nums;
    int k;
    private Data[][] memo;
    private long[] sum;

    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        this.n = nums.length;
        this.nums = nums;
        this.k = k;
        memo = new Data[nums.length][4];
        sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        List<Integer> idx = dfs(0, 3).idx;
        Collections.reverse(idx);
        return idx.stream().mapToInt(v -> v).toArray();
    }

    private Data dfs(int i, int j) {
        if (n - i < k * j) {
            return new Data(new ArrayList<>(), Integer.MIN_VALUE / 2);
        }
        if (j == 0) {
            return new Data(new ArrayList<>(), 0);
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        Data res = null;
        Data a = dfs(i + 1, j);
        Data b = dfs(i + k, j - 1);
        if (a.sum > b.sum + sum[i + k] - sum[i]) {
            res = new Data(new ArrayList<>(a.idx), a.sum);
        } else {
            ArrayList<Integer> list = new ArrayList<>(b.idx);
            list.add(i);
            res = new Data(list, b.sum + sum[i + k] - sum[i]);
        }
        return memo[i][j] = res;
    }

    class Data {
        List<Integer> idx;
        long sum;

        public Data(List<Integer> idx, long sum) {
            this.idx = idx;
            this.sum = sum;
        }
    }

    public int countPairs1(List<Integer> nums, int target) {
        int n = nums.size();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums.get(i) + nums.get(j) < target) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public int countPairs2(List<Integer> nums, int target) {
        Collections.sort(nums);
        int ans = 0;
        for (int i = 0; i < nums.size(); i++) {
            int j = binarySearch0(nums, target - nums.get(i));
            ans += j > i ? j - i : 0;
        }
        return ans;
    }

    private int binarySearch0(List<Integer> nums, int i) {
        int l = 0, r = nums.size() - 1;
        while (l < r) {
            int mid = (l + r + 1) / 2;
            if (nums.get(mid) < i) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        if (nums.get(0) >= i) {
            return -1;
        }
        return l;
    }

    public int countPairs(List<Integer> nums, int target) {
        Collections.sort(nums);
        int res = 0;
        for (int i = 0, j = nums.size() - 1; i < j; i++) {
            while (i < j && nums.get(i) + nums.get(j) >= target) {
                j--;
            }
            res += j - i;
        }
        return res;
    }

    public int maximumGap(int[] nums) {
        Arrays.sort(nums);
        int max = 0;
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(nums[i] - nums[i - 1], max);
        }
        return max;
    }

    public int compareVersion1(String version1, String version2) {
        String[] s1 = version1.split("\\.");
        String[] s2 = version2.split("\\.");
        for (int i = 0; i < s1.length; i++) {
            int v1 = Integer.parseInt(s1[i]);
            if (i <= s2.length - 1) {
                int v2 = Integer.parseInt(s2[i]);
                if (v1 > v2) {
                    return 1;
                } else if (v1 < v2) {
                    return -1;
                }
            } else {
                if (v1 > 0) {
                    return 1;
                }
            }
        }

        if (s2.length > s1.length) {
            for (int i = s1.length; i < s2.length; i++) {
                if (Integer.parseInt(s2[i]) > 0) {
                    return -1;
                }
            }
        }
        return 0;
    }

    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        for (int i = 0; i < v1.length || i < v2.length; i++) {
            int x = 0, y = 0;
            if (i < v1.length) {
                x = Integer.parseInt(v1[i]);
            }
            if (i < v2.length) {
                y = Integer.parseInt(v2[i]);
            }
            if (x > y) {
                return 1;
            }
            if (x < y) {
                return -1;
            }
        }
        return 0;
    }

    public String fractionToDecimal1(int numerator, int denominator) {
        long numeratorLong = (long) numerator;
        long denominatorLong = (long) denominator;
        if (numeratorLong % denominatorLong == 0) {
            return String.valueOf(numeratorLong / denominatorLong);
        }

        StringBuilder sb = new StringBuilder();
        if (numeratorLong < 0 ^ denominatorLong < 0) {
            sb.append("-");
        }

        numeratorLong = Math.abs(numeratorLong);
        denominatorLong = Math.abs(denominatorLong);
        long integerPart = numeratorLong / denominatorLong;
        sb.append(integerPart);
        sb.append('.');

        StringBuilder fractionPart = new StringBuilder();
        HashMap<Long, Integer> remainderIndexMap = new HashMap<>();
        long remainder = numeratorLong % denominatorLong;
        int index = 0;
        while (remainder != 0 && !remainderIndexMap.containsKey(remainder)) {
            remainderIndexMap.put(remainder, index);
            remainder *= 10;
            fractionPart.append(remainder / denominatorLong);
            remainder %= denominatorLong;
            index++;
        }

        if (remainder != 0) {
            int insertIndex = remainderIndexMap.get(remainder);
            fractionPart.insert(insertIndex, '(');
            fractionPart.append(')');
        }
        sb.append(fractionPart.toString());
        return sb.toString();
    }

    public String fractionToDecimal(int numerator, int denominator) {
        long a = numerator, b = denominator;
        if (a % b == 0) return String.valueOf(a / b);
        StringBuilder sb = new StringBuilder();
        if (a * b < 0) sb.append("-");
        a = Math.abs(a);
        b = Math.abs(b);
        sb.append(String.valueOf(a / b) + ".");
        a %= b;
        HashMap<Long, Integer> map = new HashMap<>();
        while (a != 0) {
            map.put(a, sb.length());
            a *= 10;
            sb.append(a / b);
            a %= b;
            if (map.containsKey(a)) {
                int u = map.get(a);
                return String.format("s%(s%)", sb.substring(0, u), sb.substring(u));
            }
        }
        return sb.toString();
    }

    public int titleToNumber(String columnTitle) {
        int length = columnTitle.length();
        char[] chars = columnTitle.toCharArray();
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            int num = chars[i] - 'A' + 1;
            sum = sum * 26 + num;
        }
        return sum;
    }

    private static final int MOD = (int) 1e9 + 7;

    public int sumSubarrayMins(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int min = arr[i];
            for (int j = i; j < n; j++) {
                min = Math.min(min, arr[j]);
                ans = (ans + min) % MOD;
            }
        }
        return (int) ans;
    }

    public int nextBeautifulNumber1(int n) {
        for (int i = n + 1; i <= 1224444; i++) {
            if (isBalance(i)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isBalance(int x) {
        int[] count = new int[10];
        while (x > 10) {
            count[x % 10]++;
            x /= 10;
        }
        for (int i = 0; i < 10; i++) {
            if (count[i] > 0 && count[i] != i) {
                return false;
            }
        }
        return true;
    }

    public int nextBeautifulNumber(int n) {
        TreeSet<Integer> sets = new TreeSet<>();
        // 分层，位数为1-7
        for (int i = 1; i <= 7; i++) {
            List<List<Integer>> steps = drive(i);
            for (List<Integer> step : steps) {
                int[][] cnt = step.stream().map(x -> new int[]{x, x}).toArray(int[][]::new);
                dfs(cnt, i, 0, sets);
            }
        }
        return sets.higher(n);
    }

    private void dfs(int[][] cnt, int total, int v, TreeSet<Integer> sets) {
        if (total == 0) {
            sets.add(v);
            return;
        }
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i][1] > 0) {
                cnt[i][1]--;
                dfs(cnt, total - 1, v * 10 + cnt[i][0], sets);
                cnt[i][1]++;
            }
        }
    }

    private List<List<Integer>> drive(int i) {
        List<List<Integer>> collector = new ArrayList<>();
        layer(i, new HashSet<Integer>(), collector);
        return collector;
    }

    // 生出组合
    private void layer(int v, Set<Integer> set, List<List<Integer>> collector) {
        if (v == 0) {
            collector.add(new ArrayList<>(set));
            return;
        }
        for (int i = 1; i <= v; i++) {
            if (!set.contains(i)) {
                set.add(i);
                layer(v - i, set, collector);
                set.remove(i);
            }
        }
    }

    public int[] secondGreaterElement(int[] nums) {
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < nums.length; i++) {
            while (!pq.isEmpty() && nums[i] > nums[pq.peek()[1]]) {
                res[pq.poll()[1]] = nums[i];
            }
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                pq.offer(new int[]{nums[stack.peek()], stack.peek()});
                stack.pop();
            }
            stack.push(i);
        }
        return res;
    }

    public int numberOfBoomerangs(int[][] points) {
        int ans = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int[] p1 : points) {
            cnt.clear();
            for (int[] p2 : points) {
                int d2 = (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);
                Integer i = cnt.getOrDefault(d2, 0);
                ans += 2 * i;
                cnt.put(d2, i + 1);
            }
        }
        return ans;
    }

    private int[] dp;

    public int minExtraChar(String s, String[] dictionary) {
        HashSet<String> set = new HashSet<>();
        for (String string : dictionary) {
            set.add(string);
        }
        int len = s.length();
        dp = new int[len];
        Arrays.fill(dp, -1);
        return dfsminExtraChar(len - 1, s, set);
    }

    private int dfsminExtraChar(int idx, String s, HashSet<String> set) {
        if (idx < 0) {
            return 0;
        }
        if (dp[idx] != -1) {
            return dp[idx];
        }
        int min = dfsminExtraChar(idx - 1, s, set) + 1;
        for (int i = 0; i <= idx; i++) {
            if (set.contains(s.substring(i, idx + 1))) {
                min = Math.min(dfsminExtraChar(i - 1, s, set), min);
            }
        }
        return dp[idx] = min;
    }

    public int minLength1(String s) {
        char[] charArray = s.toCharArray();
        return minLength(charArray, s.length());
    }

    private int minLength(char[] charArray, int length) {
        String s = getAns(charArray, length);
        if (s.length() == length) {
            return length;
        }
        return minLength(s.toCharArray(), s.length());
    }

    private String getAns(char[] charArray, int ans) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == 'A') {
                if (i + 1 < charArray.length && charArray[i + 1] == 'B') {
                    i++;
                    ans -= 2;
                    continue;
                }
            }

            if (charArray[i] == 'C') {
                if (i + 1 < charArray.length && charArray[i + 1] == 'D') {
                    i++;
                    ans -= 2;
                    continue;
                }
            }
            sb.append(charArray[i]);
        }
        return sb.toString();
    }

    public int minLength2(String s) {
        while (s.contains("AB") || s.contains("CD")) {
            s = s.replace("AB", "").replace("CD", "");
        }
        return s.length();
    }

    public int minLength(String s) {
        ArrayDeque<Character> stack = new ArrayDeque<>();
        char[] charArray = s.toCharArray();
        for (char c : charArray) {
            if (!stack.isEmpty() && ((stack.peek() == 'A' && c == 'B') || (stack.peek() == 'C' && c == 'D'))) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.size();
    }

    public int addMinimum(String word) {
        char[] s = word.toCharArray();
        int ans = s[0] + 2 - s[s.length - 1];
        for (int i = 1; i < s.length; i++) {
            ans += (s[i] + 2 - s[i - 1]) % 3;
        }
        return ans;
    }

    public int countWords(String[] words1, String[] words2) {
        HashMap<String, Integer> freq1 = new HashMap<>();
        HashMap<String, Integer> freq2 = new HashMap<>();
        for (String w : words1) {
            freq1.put(w, freq1.getOrDefault(w, 0) + 1);
        }
        for (String w : words2) {
            freq2.put(w, freq2.getOrDefault(w, 0) + 1);
        }
        int res = 0;
        for (String w : freq1.keySet()) {
            if (freq1.get(w) == 1 && freq2.getOrDefault(w, 0) == 1) {
                res++;
            }
        }
        return res;
    }

    private static int N = 26;

    public String repeatLimitedString(String s, int repeatLimit) {
        int[] count = new int[N];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        StringBuilder ret = new StringBuilder();
        int m = 0;
        for (int i = N - 1, j = N - 2; i >= 0 && j >= 0; ) {
            if (count[i] == 0) {
                m = 0;
                i--;
            } else if (m < repeatLimit) {
                count[i]--;
                ret.append((char) ('a' + i));
                m++;
            } else if (j >= i || count[j] == 0) {
                j--;
            } else {
                count[j]--;
                ret.append((char) ('a' + j));
                m = 0;
            }
        }
        return ret.toString();
    }

    public ListNode deleteDuplicates1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        if (head.val == head.next.val) {
            return deleteDuplicates(head.next);
        }
        head.next = deleteDuplicates(head.next);
        return head;
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode cur = head;
        while (cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }

    public int maximumSwap(int num) {
        String s = String.valueOf(num);
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int max = i;
            boolean meet = false;
            for (int j = i + 1; j < charArray.length; j++) {
                if (!meet && charArray[j] > charArray[max]) {
                    max = j;
                    meet = true;
                } else if (meet && charArray[j] >= charArray[max]) {
                    max = j;
                }
            }
            if (max != i) {
                char tmp = charArray[i];
                charArray[i] = charArray[max];
                charArray[max] = tmp;
                break;
            }
        }
        return Integer.parseInt(new String(charArray));
    }

    public int alternatingSubarray1(int[] nums) {
        int maxLength = -1;
        int before = 0;
        int start = 0;
        for (int i = 1; i < nums.length; i++) {
            if (Math.abs(nums[i] - nums[i - 1]) == 1) {
                if (nums[i] - nums[i - 1] == 1 && (before == 0 || before == -1)) {
                    if (before == 0) {
                        start = i - 1;
                    }
                    before = 1;
                    maxLength = Math.max(maxLength, i - start + 1);
                } else if (nums[i] - nums[i - 1] == -1 && (before == 0 || before == 1)) {
                    if (before == 0) {
                        start = i - 1;
                    }
                    before = -1;
                    maxLength = Math.max(maxLength, i - start + 1);
                } else {
                    before = 0;
                }
                if (before == 0) {
                    i--;
                }
            } else {
                before = 0;
            }
        }
        return maxLength;
    }

    public int alternatingSubarray(int[] nums) {
        int ans = -1;
        int i = 0, n = nums.length;
        while (i < n - 1) {
            if (nums[i + 1] - nums[i] != 1) {
                i++;
                continue;
            }
            int i0 = i;
            i += 2;
            while (i < n && nums[i] == nums[i - 2]) {
                i++;
            }
            ans = Math.max(ans, i - i0);
            i--;
        }
        return ans;
    }

    // 暴力
    public long maximumSumOfHeights3(List<Integer> maxHeights) {
        long max = 0;
        for (int i = 0; i < maxHeights.size(); i++) {
            long sum = maxHeights.get(i);
            sum += getSumBefore(maxHeights, i);
            sum += getSumAfter(maxHeights, i);
            max = Math.max(max, sum);
        }
        return max;
    }

    public int sumIndicesWithKSetBits(List<Integer> nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (bigCount(nums.get(i)) == k) {
                ans += nums.get(i);
            }
        }
        return ans;
    }

    private int bigCount(int x) {
        int cnt = 0;
        while (x != 0) {
            cnt += (x % 2);
            x /= 2;
        }
        return cnt;
    }

    private long getSumAfter(List<Integer> maxHeights, int idx) {
        if (idx >= maxHeights.size() - 1) {
            return 0;
        }
        long sum = 0;
        int max = maxHeights.get(idx);
        for (int i = idx + 1; i < maxHeights.size(); i++) {
            max = Math.min(max, maxHeights.get(i));
            sum += max;
        }
        return sum;
    }

    private long getSumBefore(List<Integer> maxHeights, int idx) {
        if (idx <= 0) {
            return 0;
        }
        long sum = 0;
        int max = maxHeights.get(idx);
        for (int i = idx - 1; i >= 0; i--) {
            max = Math.min(max, maxHeights.get(i));
            sum += max;
        }
        return sum;
    }

    public long maximumSumOfHeights(List<Integer> maxHeights) {
        long res = 0;
        int n = maxHeights.size();
        long[] pre = new long[n];
        long[] suff = new long[n];
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack1.isEmpty() && maxHeights.get(stack1.peek()) > maxHeights.get(i)) {
                stack1.pop();
            }
            if (stack1.isEmpty()) {
                pre[i] = (long) (i + 1) * maxHeights.get(i);
            } else {
                pre[i] = pre[stack1.peek()] + (long) (i - stack1.peek()) * maxHeights.get(i);
            }
            stack1.push(i);
        }
        for (int i = n - 1; i >= 0; i--) {
            while (!stack2.isEmpty() && maxHeights.get(stack2.peek()) > maxHeights.get(i)) {
                stack2.pop();
            }
            if (stack2.isEmpty()) {
                suff[i] = (long) (n - i) * maxHeights.get(i);
            } else {
                suff[i] = suff[stack2.peek()] + (long) (stack2.peek() - i) * maxHeights.get(i);
            }
            stack2.push(i);
        }
        for (int i = 0; i < n; i++) {
            res = Math.max(res, pre[i] + suff[i] - maxHeights.get(i));
        }
        return res;
    }

    int[][] pairs;

    public int findLongestChain1(int[][] pairs) {
        // 按照起点排序
        Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
        int n = pairs.length;
        this.pairs = pairs;
        cache = new int[n];
        return dfs(n - 1);
    }

    // 以pairs【i】为结尾的最长链长，结果一定为最后一个元素
    private int dfs(int i) {
        if (i < 0) {
            return 0;
        }
        if (cache[i] != 0) {
            return cache[i];
        }
        // 最少为1
        int max = 1;
        // 起点小于当前起点
        for (int j = 0; j < i; j++) {
            if (pairs[i][0] > pairs[j][1]) {
                // 取所有链最长的一条
                max = Math.max(dfs(j) + 1, max);
            }
        }
        return cache[i] = max;
    }

    public int findLongestChain2(int[][] pairs) {
        int cur = Integer.MIN_VALUE, res = 0;
        Arrays.sort(pairs, (a, b) -> a[1] - b[1]);
        for (int[] p : pairs) {
            if (cur < p[0]) {
                cur = p[1];
                res++;
            }
        }
        return res;
    }

    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, (a, b) -> a[0] - b[0]);

        ArrayList<Integer> arr = new ArrayList<>();
        for (int[] p : pairs) {
            int x = p[0], y = p[1];
            if (arr.isEmpty() || x > arr.get(
                    arr.size() - 1)) {
                arr.add(y);
            } else {
                int idx = binarySearch(arr, x);
                arr.set(idx, Math.min(arr.get(idx), y));
            }
        }
        return arr.size();
    }

    private int binarySearch(List<Integer> arr, int x) {
        int low = 0, high = arr.size() - 1;
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr.get(mid) >= x) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public int minimumSeconds(List<Integer> nums) {
        int n = nums.size();
        HashMap<Integer, List<Integer>> pos = new HashMap<>();
        for (int i = 0; i < n; i++) {
            pos.computeIfAbsent(nums.get(i), k -> new ArrayList<>()).add(i);
        }
        int ans = n;
        for (List<Integer> a : pos.values()) {
            // 首尾相连
            int mx = n - a.get(a.size() - 1) + a.get(0);
            for (int i = 1; i < a.size(); i++) {
                mx = Math.max(a.get(i) - a.get(i - 1), mx);
            }
            ans = Math.min(ans, mx);
        }
        return ans / 2;
    }

    public int[] distinctDifferenceArray(int[] nums) {
        int n = nums.length;
        int ans[] = new int[n];
        HashSet<Integer> set = new HashSet<>();
        int pre[] = new int[n];
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
            pre[i] = set.size();
        }
        set.clear();
        int suf[] = new int[n];
        for (int i = nums.length - 1; i >= 0; i--) {
            suf[i] = set.size();
            set.add(nums[i]);
        }
        for (int i = 0; i < n; i++) {
            ans[i] = pre[i] - suf[i];
        }
        return ans;
    }

    public int countSubstrings(String s) {
        char[] charArray = s.toCharArray();
        int n = charArray.length;
        check = new int[n][n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (check(charArray, i, j) == 1) {
                    res++;
                }
            }
        }
        return res;
    }

    private int[][] check;

    private int check(char[] charArray, int i, int j) {
        if (check[i][j] != 0) {
            return check[i][j];
        }
        if (i == j) {
            return check[i][j] = 1;
        } else if (j == i + 1) {
            return check[i][j] = charArray[i] == charArray[j] ? 1 : -1;
        } else {
            if (charArray[i] == charArray[j]) {
                return check[i][j] = check(charArray, i + 1, j - 1);
            } else {
                return check[i][j] = -1;
            }
        }
    }

    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
        Arrays.sort(ids, (i, j) -> aliceValues[j] + bobValues[j] - aliceValues[i] - bobValues[i]);
        int diff = 0;
        for (int i = 0; i < n; i++) {
            diff += i % 2 == 0 ? aliceValues[ids[i]] : -bobValues[ids[i]];
        }
        return Integer.compare(diff, 0);
    }

    public boolean canWinNim(int n) {
        if (n <= 3) {
            return true;
        }
        return n % 4 != 0;
    }

    int[] cache;

    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        pq.offer(new int[]{0, dp[0]});
        for (int i = 1; i < n; i++) {
            while (pq.peek()[0] < i - k) {
                pq.poll();
            }
            dp[i] = nums[i] + pq.peek()[1];
            pq.offer(new int[]{i, dp[i]});
        }
        return dp[n - 1];
    }

    public int magicTower(int[] nums) {
        long sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < 0) {
            return -1;
        }
        int ans = 0;
        long hp = 1;
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int x : nums) {
            if (x < 0) {
                heap.offer(x);
            }
            hp += x;
            if (hp < 1) {
                hp -= heap.poll();
                ans++;
            }
        }
        return ans;
    }

    private int count = 0;

    private int magicTower(List<Integer> nums, int sum) {
        if (nums.isEmpty()) {
            return sum;
        }
        List<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            if (sum + nums.get(i) < 0) {
                tmp.add(nums.get(i));
                count++;
                continue;
            }
            sum += nums.get(i);
        }
        if (nums.size() == tmp.size()) {
            return Integer.MIN_VALUE;
        }
        return magicTower(tmp, sum);
    }

    private int me[];

    public boolean validPartition(int[] nums) {
        me = new int[nums.length];
        Arrays.fill(me, -1);
        return validPartition(nums, 0);
    }

    private boolean validPartition(int[] nums, int i) {
        if (i >= nums.length) {
            return true;
        }
        if (me[i] != -1) {
            return me[i] == 1;
        }
        if (i + 1 < nums.length && nums[i] == nums[i + 1]) {
            if (i + 2 < nums.length && nums[i + 1] == nums[i + 2]) {
                me[i] = validPartition(nums, i + 3) || validPartition(nums, i + 2) ? 1 : -1;
                return me[i] == 1;
            }
            return (me[i] = validPartition(nums, i + 2) ? 1 : -1) == 1;
        }
        if (i + 2 < nums.length && nums[i] + 1 == nums[i + 1] && nums[i + 1] + 1 == nums[i + 2]) {
            return (me[i] = validPartition(nums, i + 3) ? 1 : -1) == 1;
        }

        me[i] = -1;
        return false;
    }




        @Test
    public void test() {
        magicTower(new int[]{100, 100, 100, -250, -60, -140, -50, -50, 100, 150});
    }

}
