#include <stdio.h>

#define INPUT_LENGTH 5

static double lr = 1;

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
	double res = w[0]*input[0]; 	// threshold
	for (int i = 1; i < INPUT_LENGTH + 1; i++)
	{
		res += w[i]*input[i];
	}
	return sgn(res);
}

void delta_rule(double S, double o, double * E)
{
	for (int i = 0; i < INPUT_LENGTH + 1; i++)
	{
		w[i] += lr*(S - o)*E[i];
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
		}
		else
		{
			correct++;
		}
	}
}

int main()
{
	struct learning_pattern example[] = 
	{
		{
			.input = {0,0,0,0,0},
			.output = -1
		},/*
		{
			.input = {1,1,1,1,1},
			.output = 1
		},*/
		{
			.input = {1,0,0,0,0},
			.output = -1
		}
	};
	learn(example, 2);
	double test[] = {1,0,0,0,0};
	printf("AND(1,0,0,0,0) = %lf\n", calculate(test));
	
	return 0;
}
