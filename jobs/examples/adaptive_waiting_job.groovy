package examples


job('simple-waiting-job') {
    publishers {
        naginatorPublisher {
            maxSchedule 10

            rerunIfUnstable true
            rerunMatrixPart false
        }
    }
}

//
//job('adaptive-waiting-job') {
//    publishers {
//        naginatorPublisher {
//            maxSchedule 10
//
//            delay {
//                progressiveDelay {
//                    increment 3 //seconds
//                    max 9 //seconds
//                }
//            }
//            checkRegexp true
//            regexpForRerun 'regexp to determine whether there is a point in reruning it'
//
//            rerunIfUnstable true
//            rerunMatrixPart false
//
//        }
//    }
//}
