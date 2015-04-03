package org.gentoo.developers;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by johu on 03.04.15.
 */
public interface DeveloperRepository extends PagingAndSortingRepository<Developer, String> {

}
