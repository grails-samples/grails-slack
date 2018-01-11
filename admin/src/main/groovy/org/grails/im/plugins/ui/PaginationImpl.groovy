package org.grails.im.plugins.ui

import groovy.transform.CompileStatic

@CompileStatic
class PaginationImpl implements Pagination {
    Integer max
    Integer offset
    Integer total
}
