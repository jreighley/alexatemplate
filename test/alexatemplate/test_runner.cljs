(ns alexatemplate.test-runner
 (:require [doo.runner :refer-macros [doo-tests]]
           [alexatemplate.core-test]
           [cljs.nodejs :as nodejs]))

(try
  (.install (nodejs/require "source-map-support"))
  (catch :default _))

(doo-tests
 'alexatemplate.core-test)
