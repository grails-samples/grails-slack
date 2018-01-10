package org.grails.im

import groovy.transform.CompileStatic

@CompileStatic
class PaginationImpl implements Pagination {
    Integer max
    Integer offset
    Integer total
}
