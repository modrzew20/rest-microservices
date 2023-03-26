package com.example.smartcode.repository.impl;

import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.exception.InvalidParameterException;
import com.example.smartcode.repository.ShapeCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShapeCustomRepositoryImpl implements ShapeCustomRepository {

    private static final String CREATED_AT = "createdAt";
    private static final String FROM = "From";
    private static final String TO = "To";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Shape> findAll(Map<String, String> params) throws InvalidParameterException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Shape> query = cb.createQuery(Shape.class);
            Root<Shape> root = query.from(Shape.class);

            List<Predicate> predicates = getPredicateList(params, cb, root);
            TypedQuery<Shape> typedQuery = entityManager.createQuery(query.select(root).where(predicates.toArray(new Predicate[0])));
            return typedQuery.getResultList();
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException();
        }
    }

    private List<Predicate> getPredicateList(Map<String, String> params, CriteriaBuilder cb, Root<Shape> root) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (param.getKey().contains(TO)) {
                predicates.add(getLessOrEqualValuePredicate(cb, root, param.getKey(), param.getValue()));
            } else if (param.getKey().contains(FROM)) {
                predicates.add(getGreaterOrEqualValuePredicate(cb, root, param.getKey(), param.getValue()));
            } else {
                predicates.add(getEqualValuePredicate(cb, root, param.getKey(), param.getValue()));
            }
        }
        return predicates;
    }

    private Predicate getEqualValuePredicate(CriteriaBuilder cb, Root<Shape> root, String key, String value) {
        if(key.equals(CREATED_AT)) {
            return cb.equal(root.get(key), LocalDateTime.parse(value));
        }
        return cb.equal(root.get(key), value.toLowerCase());
    }

    private Predicate getLessOrEqualValuePredicate(CriteriaBuilder cb, Root<Shape> root, String key, String value) {
        int splitIndex = key.indexOf(TO);
        String attributeName = key.substring(0, splitIndex);

        if(attributeName.equals(CREATED_AT)) {
            return cb.lessThanOrEqualTo(root.get(attributeName), LocalDateTime.parse(value));
        }
        return cb.lessThanOrEqualTo(root.get(attributeName), value);
    }

    private Predicate getGreaterOrEqualValuePredicate(CriteriaBuilder cb, Root<Shape> root, String key, String value) {
        int splitIndex = key.indexOf(FROM);
        String attributeName = key.substring(0, splitIndex);

        if(attributeName.equals(CREATED_AT)) {
            return cb.greaterThanOrEqualTo(root.get(attributeName), LocalDateTime.parse(value));
        }
        return cb.greaterThanOrEqualTo(root.get(attributeName), value);
    }




}
