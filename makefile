verifier.out: tsuyosshi_verifier.o
	g++ -o verifier.out tsuyosshi_verifier.o -O3

tsuyosshi_verifier.o: tsuyosshi_verifier.cpp
	g++ -c tsuyosshi_verifier.cpp -O3
