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
static const int MAX_N = 52;

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
vector<AnswerSeed> answer;

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
		if (mergedSeed != ans.seed) {
			cout << i << "回目の交配結果が正しくありません。" << endl;
			cout << testSeed[ans.x - 1] << " " << testSeed[ans.y - 1] << "交配結果は" << mergedSeed << "となります。" << endl;
			return;
		}
	}
	if (!isVanish(mergedSeed)) {
		cout << "最終結果が不正です。 [!] を生成できません。" << endl;
	}
}

int main() {
	cin >> N;
	for (int i = 0; i < N; ++i) {
		string temp;
		cin >> temp;
		testSeed.push_back(temp);
	}
	ConfirmFormat();
	ConfirmAnswer();
	return 0;
}
