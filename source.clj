(let [a BT_Numeric] 2 a)
(print (+ 1 3))

(defn x a b (+ a b))
(print (x 3 5))

(defn fact n (if (= 0 n) 1 (* n (fact (- n 1)))))
(print (fact 5))

