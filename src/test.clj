(def invoice (clojure.edn/read-string (slurp "invoice.edn"))) ;slurp reads the file and returns its info as a string,

;Need a function that takes an invoice as an argument and using the ->> macro, finds all the invoice items that satisfy the following conditions:
;-	At least have one item that has IVA 19
;-	At least one item has retention :ret_fuente 1%
;-	Every item must satisfy EXACTLY one of the above two conditions. This means that an item cannot have BOTH IVA 19 and retention :ret_fuente 1%. It must have one or the other.
;- if an item has both :IVA 19 and :ret_fuente 1%, it should be discarded from the result.



(defn find-invoice-items [invoice]
  (->> invoice
       :invoice/items
       (filter (fn [invoice-item]
                 (or (some #(= (:tax/rate %) 19) (:taxable/taxes invoice-item))
                     (some #(= (:retention/rate %) 1) (:retentionable/retentions invoice-item)))))))



