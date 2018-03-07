#include <stdio.h>

#define INPUT_LENGTH 5

static double lr = 1;
static int learning_time = 0;
static double w[INPUT_LENGTH+1];

struct learning_pattern
{
	double input[INPUT_LENGTH];
	double output;
};

double sgn(double x)
{
	return (x >= 0 ? 1 : -1);
}

double calculate(double * input)
{
	double res = -w[0]; 	// threshold
	for (int i = 1; i < INPUT_LENGTH + 1; i++)
	{
		res += w[i]*input[i-1];
	}
	return sgn(res);
}

void delta_rule(double S, double o, double * E)
{
	w[0] += lr*(o - S);
	for (int i = 1; i < INPUT_LENGTH + 1; i++)
	{
		w[i] += lr*(S - o)*E[i-1];
	}
}

void learn(struct learning_pattern * lp, int length)
{
	for (int i = 0; i < INPUT_LENGTH + 1; i++)
	{
		w[i] = 0;
	}
	int correct = 0;
	for (int i = 0; correct != length; i = (i+1)%length)
	{
		double expected = lp[i].output;
		double obtained = calculate(lp[i].input);
		if (expected != obtained)
		{
			delta_rule(expected, obtained, lp[i].input);
			correct = 0;
			learning_time++;
		}
		else
		{
			correct++;
		}
	}
}

void test()
{
	struct learning_pattern tp;
	double correct = 0;
	for (int i = 0; i < (1<<INPUT_LENGTH); i++)
	{
		if (i == 31)
		{
			tp.output = 1;
		}
		else
		{
			tp.output = -1;
		}
		int k = i;
		for (int j = 0; j < INPUT_LENGTH; j++, k >>= 1)
		{
			tp.input[j] = k%2;
		}
		double ans = calculate(tp.input);
		if (ans == tp.output) correct++;
		printf("AND(");
		for (int j = 0; j < INPUT_LENGTH; j++)
		{
			printf("%d", (int) tp.input[j]);
			(j == INPUT_LENGTH-1 ? putchar(')') : putchar(','));
		}
		printf(" = %d\n", (int) ans);
	}
	correct /= (1<<INPUT_LENGTH);
	correct *= 100;
	printf("PASSED: %lf%%\n", correct);
}

int main()
{
	struct learning_pattern example[] = 
	{
		{
			.input = {0,0,0,0,0},
			.output = -1
		},
		{
			.input = {1,1,1,1,1},
			.output = 1
		},
		{
			.input = {1,1,1,1,0},
			.output = -1
		},
		{
			.input = {0,1,1,1,1},
			.output = -1
		}
	};
	learn(example, 4);
	test();
	printf("Learning time: %d\n", learning_time);
	return 0;
}
