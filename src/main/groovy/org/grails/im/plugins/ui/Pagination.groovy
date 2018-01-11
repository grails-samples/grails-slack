package org.grails.im.plugins.ui

import groovy.transform.CompileStatic

@CompileStatic
interface Pagination {
    Integer getMax()
    Integer getOffset()
    Integer getTotal()
}