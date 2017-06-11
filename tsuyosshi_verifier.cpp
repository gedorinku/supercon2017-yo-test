#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <cmath>
#include <climits>
#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include <algorithm>
#include <utility>
#include <set>
#include <map>
#include <stack>
#include <queue>
#include <deque>
#include <functional>
#include <string.h>
using namespace std;
static const int INF = INT_MAX;
static const int MAX_N = 53;

struct AnswerSeed {
	int x, y;
	string seed;
	AnswerSeed() {}
	AnswerSeed(int x, int y, string seed) : x(x), y(y), seed(seed) {}
};

int N;
int answerNum;
bool isSucces;
vector<string> testSeed;
vector<int> AlphaIndex[26][2];
vector<int> useSeed;
vector<AnswerSeed> answer;

void SelectSeed() {
	vector<int> NoSeed[26][2];
	for (int i = 0; i < 26; ++i) {
		if ((AlphaIndex[i][0].size() == 0) ^ (AlphaIndex[i][1].size() == 0)) {
			int UP = (AlphaIndex[i][0].size() == 0) ? 1 : 0;
			for (auto A : AlphaIndex[i][UP]) {
				for (int j = 0; j < 26; j++) {
					for (int k = 0; k < 2; ++k) {
						NoSeed[j][k].push_back(A);
					}
				}
			}
		}
	}
	for (int i = 0; i < 26; ++i) {
		for (int j = 0; j < 2; ++j) {
			for (auto A : AlphaIndex[i][j]) {
				bool isErase = false;
				for (auto B : NoSeed[i][j]) {
					if (A == B) {
						isErase = true;
						break;
					}
				}
				if (!isErase)useSeed.push_back(A);
			}
		}
	}

	sort(useSeed.begin(), useSeed.end());
	useSeed.erase(unique(useSeed.begin(), useSeed.end()), useSeed.end());
}

string Merge(string A, string B) {
	string result;
	int i = 0, j = 0;
	while (true) {
		char a = A[i], b = B[j];
		if (i == A.size() && j == B.size())break;
		if (i == A.size())a = 'z' + 1;
		if (j == B.size())b = 'z' + 1;
		if (tolower(a) < tolower(b)) {
			if (result[result.size() - 1] == a) {
				i++;
				continue;
			}
			i++;
			result.push_back(a);
		}
		else if (tolower(a) > tolower(b)) {
			if (result[result.size() - 1] == b) {
				j++;
				continue;
			}
			j++;
			result.push_back(b);
		}
		else {
			if (a > b) {
				if (result[result.size() - 1] == a) {
					i++;
					continue;
				}
				i++;
				result.push_back(a);
			}
			else {
				if (result[result.size() - 1] == b) {
					j++;
					continue;
				}
				j++;
				result.push_back(b);
			}
		}
	}
	return result;
}

bool isVanish(string seed) {
	if (seed.size() % 2 != 0)return false;
	for (int i = 0; i<seed.size();) {
		if (seed[i] == seed[i + 1] + 32)i += 2;
		else return false;
	}
	return true;
}


void ConfirmFormat() {
	string result;
	cin >> result;
	SelectSeed();
	if (result == "YES" && useSeed.size() == 0)cout << "[[結果はYESではありません。交配は不可能です。]]" << endl;
	if (result == "NO" && useSeed.size() != 0)cout << "[[結果はNOではありません。交配は可能です。]]" << endl;
	cin >> answerNum;
	for (int i = 0; i < answerNum; ++i) {
		int x, y;
		string seed;
		cin >> x >> y >> seed;
		answer.push_back(AnswerSeed(x, y, seed));
		if (i == answerNum - 1 && seed != "!") {
			cout << "[[ 最終結果が ! ではありません ]]" << endl;
		}
	}
}

void ConfirmAnswer() {
	string mergedSeed;
	for (int i = 0; i < answerNum - 1; i++) {
		AnswerSeed ans = answer[i];
		mergedSeed = Merge(testSeed[ans.x - 1], testSeed[ans.y - 1]);
		testSeed.push_back(mergedSeed);
		cout << mergedSeed << " " << ans.seed << endl;
		cout << (mergedSeed == ans.seed) << endl;
		if (mergedSeed != ans.seed) {
			cout << "[[" << i + 1 << "回目の交配結果が正しくありません。" << "]]" << endl;
			cout << "[[" << "交配結果は" << mergedSeed << "となります。" << "]]" << endl;
			return;
		}
	}
	if (!isVanish(mergedSeed)) {
		cout << "[[最終結果が不正です。 [!] を生成できません。]]" << endl;
	}
}

int main() {
	cin >> N;
	for (int i = 0; i < N; ++i) {
		string temp;
		cin >> temp;
		testSeed.push_back(temp);
		for (auto c : temp) {
			bool UP = islower(c) ? 0 : 1;
			AlphaIndex[tolower(c) - 'a'][UP].push_back(i);
		}
	}
	ConfirmFormat();
	ConfirmAnswer();
	return 0;
}
