package com.example.demo.calculator.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCalculator is a Querydsl query type for Calculator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalculator extends EntityPathBase<Calculator> {

    private static final long serialVersionUID = -457783653L;

    public static final QCalculator calculator = new QCalculator("calculator");

    public final NumberPath<Integer> calculatorAnswer = createNumber("calculatorAnswer", Integer.class);

    public final StringPath calculatorFormula = createString("calculatorFormula");

    public final NumberPath<Integer> calculatorId = createNumber("calculatorId", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public QCalculator(String variable) {
        super(Calculator.class, forVariable(variable));
    }

    public QCalculator(Path<? extends Calculator> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCalculator(PathMetadata metadata) {
        super(Calculator.class, metadata);
    }

}

