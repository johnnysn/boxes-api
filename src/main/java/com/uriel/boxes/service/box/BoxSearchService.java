package com.uriel.boxes.service.box;

import com.uriel.boxes.controller.search.BoxSearchParams;
import com.uriel.boxes.data.entity.Box;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
class BoxSearchService {
    @PersistenceContext
    private EntityManager entityManager;

    public Page<Box> execute(BoxSearchParams params, Pageable pageable) {
        var cb = entityManager.getCriteriaBuilder();

        var query = cb.createQuery(Box.class);
        var root = query.from(Box.class);

        Predicate[] predicates = createPredicates(params, cb, root);
        query.where(predicates);

        // Aplica ordenação do pageable
        if (pageable.getSort().isSorted()) {
            var orders = new ArrayList<Order>();
            pageable.getSort().forEach(order -> {
                if (order.isAscending()) {
                    orders.add(cb.asc(root.get(order.getProperty())));
                } else {
                    orders.add(cb.desc(root.get(order.getProperty())));
                }
            });
            query.orderBy(orders);
        }

        var typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset()); // Pula offset registros
        typedQuery.setMaxResults(pageable.getPageSize());      // Pega page_size registros

        var content = typedQuery.getResultList();

        // Query de contagem
        var countQuery = cb.createQuery(Long.class);
        Root<Box> countRoot = countQuery.from(Box.class);

        Predicate[] countPredicates = createPredicates(params, cb, countRoot);
        countQuery.select(cb.count(countRoot)).where(countPredicates);
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate[] createPredicates(BoxSearchParams params, CriteriaBuilder cb, Root<Box> root) {
        var predicates = new ArrayList<Predicate>();
        var textPredicates = new ArrayList<Predicate>();
        var joinUser = root.join("user");

        if (params.getUserId() != null) {
            predicates.add(cb.equal(joinUser.get("id"), params.getUserId()));
        }

        if (!Strings.isBlank(params.getLabel())) {
            textPredicates.add(cb.like(
                    cb.lower(root.get("label")),
                    "%" + params.getLabel().trim().toLowerCase() + "%"
            ));
        }

        if (!Strings.isBlank(params.getDescription())) {
            textPredicates.add(cb.like(
                    cb.lower(root.get("description")),
                    "%" + params.getDescription().trim().toLowerCase() + "%"
            ));
        }

        if (!textPredicates.isEmpty()) {
            var textPredicate = Boolean.TRUE.equals(params.getOr()) ?
                    cb.or(textPredicates.toArray(new Predicate[0])) :
                    cb.and(textPredicates.toArray(new Predicate[0]));
            predicates.add(textPredicate);
        }

        return predicates.toArray(new Predicate[0]);
    }
}
