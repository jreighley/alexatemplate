(ns alexatemplate.core
  (:require [cljs-lambda.util :as lambda]
            [cljs-lambda.context :as ctx]
            [cljs-lambda.macros :refer-macros [deflambda]]
            [cljs.reader :refer [read-string]]
            [cljs.nodejs :as nodejs]))

(deflambda alexa-magic "This function receives the JSON input from the Alexa function and creates a map of the items of interest.  You can then dispatch on the input map to call functions to build the proper response map"[inputjson]
  (let [jsonstring (js->clj inputjson)
        reqestdetails { :sessionid (-> jsonstring :session :sessionId)
                        :applicationid (-> jsonstring :session :application :applicationId)
                        :userid (-> jsonstring :session :user :userId)
                        :reqtype (-> jsonstring :request :type)
                        :sessionstate (-> jsonstring :session :attributes)
                        :intent (-> jsonstring :request :intent :name)
                        :slots (-> jsonstring :request :intent :slots :<slotname> :value)
                        :version (:version jsonstring)
                        :timestamp (-> jsonstring :request :timestamp)}]
   (cond
     (= (:reqtype reqestdetails) "LaunchRequest") {:version 1.0 :response {:outputSpeech {:type "PlainText" :text "Welcome Clojurescript!"  }:shouldEndSession false}}
     (= (:intent reqestdetails) "GetHello") {:version 1.0 :response {:outputSpeech {:type "PlainText" :text "HELLO!"  }:shouldEndSession false}}
     (= (:intent reqestdetails) "GetGoodbye") {:version 1.0 :response {:outputSpeech {:type "PlainText" :text "BYE!"  }:shouldEndSession false}}
     :else {:version 1.0 :response {:outputSpeech {:type "PlainText" :text "Welcome Clojurescript!"  }:shouldEndSession false}})))

     ;; You can replace these response maps with calls to other functions that return similar maps.  The maps should correspond to the JSON requirements set forth
     ;; here:   https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/alexa-skills-kit-interface-reference#response-format
