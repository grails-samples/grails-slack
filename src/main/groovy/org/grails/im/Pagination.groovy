package org.grails.im

import groovy.transform.CompileStatic

@CompileStatic
interface Pagination {
    Integer getMax()
    Integer getOffset()
    Integer getTotal()
}