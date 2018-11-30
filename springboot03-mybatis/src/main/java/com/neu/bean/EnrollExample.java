package com.neu.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EnrollExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andEnrIdIsNull() {
            addCriterion("enr_id is null");
            return (Criteria) this;
        }

        public Criteria andEnrIdIsNotNull() {
            addCriterion("enr_id is not null");
            return (Criteria) this;
        }

        public Criteria andEnrIdEqualTo(Integer value) {
            addCriterion("enr_id =", value, "enrId");
            return (Criteria) this;
        }

        public Criteria andEnrIdNotEqualTo(Integer value) {
            addCriterion("enr_id <>", value, "enrId");
            return (Criteria) this;
        }

        public Criteria andEnrIdGreaterThan(Integer value) {
            addCriterion("enr_id >", value, "enrId");
            return (Criteria) this;
        }

        public Criteria andEnrIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("enr_id >=", value, "enrId");
            return (Criteria) this;
        }

        public Criteria andEnrIdLessThan(Integer value) {
            addCriterion("enr_id <", value, "enrId");
            return (Criteria) this;
        }

        public Criteria andEnrIdLessThanOrEqualTo(Integer value) {
            addCriterion("enr_id <=", value, "enrId");
            return (Criteria) this;
        }

        public Criteria andEnrIdIn(List<Integer> values) {
            addCriterion("enr_id in", values, "enrId");
            return (Criteria) this;
        }

        public Criteria andEnrIdNotIn(List<Integer> values) {
            addCriterion("enr_id not in", values, "enrId");
            return (Criteria) this;
        }

        public Criteria andEnrIdBetween(Integer value1, Integer value2) {
            addCriterion("enr_id between", value1, value2, "enrId");
            return (Criteria) this;
        }

        public Criteria andEnrIdNotBetween(Integer value1, Integer value2) {
            addCriterion("enr_id not between", value1, value2, "enrId");
            return (Criteria) this;
        }

        public Criteria andEnrUserIsNull() {
            addCriterion("enr_user is null");
            return (Criteria) this;
        }

        public Criteria andEnrUserIsNotNull() {
            addCriterion("enr_user is not null");
            return (Criteria) this;
        }

        public Criteria andEnrUserEqualTo(String value) {
            addCriterion("enr_user =", value, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserNotEqualTo(String value) {
            addCriterion("enr_user <>", value, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserGreaterThan(String value) {
            addCriterion("enr_user >", value, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserGreaterThanOrEqualTo(String value) {
            addCriterion("enr_user >=", value, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserLessThan(String value) {
            addCriterion("enr_user <", value, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserLessThanOrEqualTo(String value) {
            addCriterion("enr_user <=", value, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserLike(String value) {
            addCriterion("enr_user like", value, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserNotLike(String value) {
            addCriterion("enr_user not like", value, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserIn(List<String> values) {
            addCriterion("enr_user in", values, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserNotIn(List<String> values) {
            addCriterion("enr_user not in", values, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserBetween(String value1, String value2) {
            addCriterion("enr_user between", value1, value2, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrUserNotBetween(String value1, String value2) {
            addCriterion("enr_user not between", value1, value2, "enrUser");
            return (Criteria) this;
        }

        public Criteria andEnrPublishIsNull() {
            addCriterion("enr_publish is null");
            return (Criteria) this;
        }

        public Criteria andEnrPublishIsNotNull() {
            addCriterion("enr_publish is not null");
            return (Criteria) this;
        }

        public Criteria andEnrPublishEqualTo(Integer value) {
            addCriterion("enr_publish =", value, "enrPublish");
            return (Criteria) this;
        }

        public Criteria andEnrPublishNotEqualTo(Integer value) {
            addCriterion("enr_publish <>", value, "enrPublish");
            return (Criteria) this;
        }

        public Criteria andEnrPublishGreaterThan(Integer value) {
            addCriterion("enr_publish >", value, "enrPublish");
            return (Criteria) this;
        }

        public Criteria andEnrPublishGreaterThanOrEqualTo(Integer value) {
            addCriterion("enr_publish >=", value, "enrPublish");
            return (Criteria) this;
        }

        public Criteria andEnrPublishLessThan(Integer value) {
            addCriterion("enr_publish <", value, "enrPublish");
            return (Criteria) this;
        }

        public Criteria andEnrPublishLessThanOrEqualTo(Integer value) {
            addCriterion("enr_publish <=", value, "enrPublish");
            return (Criteria) this;
        }

        public Criteria andEnrPublishIn(List<Integer> values) {
            addCriterion("enr_publish in", values, "enrPublish");
            return (Criteria) this;
        }

        public Criteria andEnrPublishNotIn(List<Integer> values) {
            addCriterion("enr_publish not in", values, "enrPublish");
            return (Criteria) this;
        }

        public Criteria andEnrPublishBetween(Integer value1, Integer value2) {
            addCriterion("enr_publish between", value1, value2, "enrPublish");
            return (Criteria) this;
        }

        public Criteria andEnrPublishNotBetween(Integer value1, Integer value2) {
            addCriterion("enr_publish not between", value1, value2, "enrPublish");
            return (Criteria) this;
        }

        public Criteria andEnrNoteIsNull() {
            addCriterion("enr_note is null");
            return (Criteria) this;
        }

        public Criteria andEnrNoteIsNotNull() {
            addCriterion("enr_note is not null");
            return (Criteria) this;
        }

        public Criteria andEnrNoteEqualTo(String value) {
            addCriterion("enr_note =", value, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteNotEqualTo(String value) {
            addCriterion("enr_note <>", value, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteGreaterThan(String value) {
            addCriterion("enr_note >", value, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteGreaterThanOrEqualTo(String value) {
            addCriterion("enr_note >=", value, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteLessThan(String value) {
            addCriterion("enr_note <", value, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteLessThanOrEqualTo(String value) {
            addCriterion("enr_note <=", value, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteLike(String value) {
            addCriterion("enr_note like", value, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteNotLike(String value) {
            addCriterion("enr_note not like", value, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteIn(List<String> values) {
            addCriterion("enr_note in", values, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteNotIn(List<String> values) {
            addCriterion("enr_note not in", values, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteBetween(String value1, String value2) {
            addCriterion("enr_note between", value1, value2, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrNoteNotBetween(String value1, String value2) {
            addCriterion("enr_note not between", value1, value2, "enrNote");
            return (Criteria) this;
        }

        public Criteria andEnrDateIsNull() {
            addCriterion("enr_date is null");
            return (Criteria) this;
        }

        public Criteria andEnrDateIsNotNull() {
            addCriterion("enr_date is not null");
            return (Criteria) this;
        }

        public Criteria andEnrDateEqualTo(Date value) {
            addCriterion("enr_date =", value, "enrDate");
            return (Criteria) this;
        }

        public Criteria andEnrDateNotEqualTo(Date value) {
            addCriterion("enr_date <>", value, "enrDate");
            return (Criteria) this;
        }

        public Criteria andEnrDateGreaterThan(Date value) {
            addCriterion("enr_date >", value, "enrDate");
            return (Criteria) this;
        }

        public Criteria andEnrDateGreaterThanOrEqualTo(Date value) {
            addCriterion("enr_date >=", value, "enrDate");
            return (Criteria) this;
        }

        public Criteria andEnrDateLessThan(Date value) {
            addCriterion("enr_date <", value, "enrDate");
            return (Criteria) this;
        }

        public Criteria andEnrDateLessThanOrEqualTo(Date value) {
            addCriterion("enr_date <=", value, "enrDate");
            return (Criteria) this;
        }

        public Criteria andEnrDateIn(List<Date> values) {
            addCriterion("enr_date in", values, "enrDate");
            return (Criteria) this;
        }

        public Criteria andEnrDateNotIn(List<Date> values) {
            addCriterion("enr_date not in", values, "enrDate");
            return (Criteria) this;
        }

        public Criteria andEnrDateBetween(Date value1, Date value2) {
            addCriterion("enr_date between", value1, value2, "enrDate");
            return (Criteria) this;
        }

        public Criteria andEnrDateNotBetween(Date value1, Date value2) {
            addCriterion("enr_date not between", value1, value2, "enrDate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}