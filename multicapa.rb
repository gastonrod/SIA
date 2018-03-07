#Learning rate
lr = 1

def calculate(W, inp)
	V = Array.new(W[0].size, 0)
	for i in (0..W.size-2)
		for j in (0..W[0].size-1)
			V[j] += W[i][j] * inp[i]
		end
	end
	V = V.map {|n,index| n -= W.last[index]}
	return V.map{|n| n == 0 ? 1 : n.abs / n)}
end

def backwards_propagation

end

def exit_hidden(o, pat, V)
    DW = Array.new(2,0)
	for i in 0..DW.size -1
		DW[i] = lr * (pat.output - o ) * derivadaG * V[i]
    end
    DW
end

def hidden_input(o, pat, DWb)
	DWa = Array.new(5) { Array.new(2,0) }
	for j in 0..DWa.size-1
		for i in 0..DWa[0].size-1
 			lr * (pat.output - o) * derivadaGi * DWb[i] * derivadaGj * pat.input[j]
 		end
 	end
 	DWa
end

def learn(lp, Wa, Wb)
	V = nil
	correct = 0
	while(correct < lp.size)
		for pat in lp
			V = calculate(Wa, pat[:input])
			out = calculate(Wb, V)
			if(out == pat[:output])
				correct++
				if(correct >= lp.size)
					break
				end
			else
				correct = 0

			end
		end
	end
end

Wa = Array.new(6) { Array.new(2) {Random.rand(0..1.0)}} #Umbral en el ultimo
Wb = Array.new(3) { Array.new(1) {Random.rand(0..1.0)}}

learning_pattern = [{:input => [-1,-1,-1,-1,-1], :output => -1}, 
					{:input => [1,-1,1,-1,1], :output => -1},
					{:input => [-1,1,-1,1,-1], :output => 1}]

