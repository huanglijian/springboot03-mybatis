package com.neu.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublishExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PublishExample() {
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

        public Criteria andPubIdIsNull() {
            addCriterion("pub_id is null");
            return (Criteria) this;
        }

        public Criteria andPubIdIsNotNull() {
            addCriterion("pub_id is not null");
            return (Criteria) this;
        }

        public Criteria andPubIdEqualTo(Integer value) {
            addCriterion("pub_id =", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdNotEqualTo(Integer value) {
            addCriterion("pub_id <>", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdGreaterThan(Integer value) {
            addCriterion("pub_id >", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("pub_id >=", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdLessThan(Integer value) {
            addCriterion("pub_id <", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdLessThanOrEqualTo(Integer value) {
            addCriterion("pub_id <=", value, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdIn(List<Integer> values) {
            addCriterion("pub_id in", values, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdNotIn(List<Integer> values) {
            addCriterion("pub_id not in", values, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdBetween(Integer value1, Integer value2) {
            addCriterion("pub_id between", value1, value2, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubIdNotBetween(Integer value1, Integer value2) {
            addCriterion("pub_id not between", value1, value2, "pubId");
            return (Criteria) this;
        }

        public Criteria andPubThemeIsNull() {
            addCriterion("pub_theme is null");
            return (Criteria) this;
        }

        public Criteria andPubThemeIsNotNull() {
            addCriterion("pub_theme is not null");
            return (Criteria) this;
        }

        public Criteria andPubThemeEqualTo(String value) {
            addCriterion("pub_theme =", value, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeNotEqualTo(String value) {
            addCriterion("pub_theme <>", value, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeGreaterThan(String value) {
            addCriterion("pub_theme >", value, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeGreaterThanOrEqualTo(String value) {
            addCriterion("pub_theme >=", value, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeLessThan(String value) {
            addCriterion("pub_theme <", value, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeLessThanOrEqualTo(String value) {
            addCriterion("pub_theme <=", value, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeLike(String value) {
            addCriterion("pub_theme like", value, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeNotLike(String value) {
            addCriterion("pub_theme not like", value, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeIn(List<String> values) {
            addCriterion("pub_theme in", values, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeNotIn(List<String> values) {
            addCriterion("pub_theme not in", values, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeBetween(String value1, String value2) {
            addCriterion("pub_theme between", value1, value2, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubThemeNotBetween(String value1, String value2) {
            addCriterion("pub_theme not between", value1, value2, "pubTheme");
            return (Criteria) this;
        }

        public Criteria andPubSceneIsNull() {
            addCriterion("pub_scene is null");
            return (Criteria) this;
        }

        public Criteria andPubSceneIsNotNull() {
            addCriterion("pub_scene is not null");
            return (Criteria) this;
        }

        public Criteria andPubSceneEqualTo(String value) {
            addCriterion("pub_scene =", value, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneNotEqualTo(String value) {
            addCriterion("pub_scene <>", value, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneGreaterThan(String value) {
            addCriterion("pub_scene >", value, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneGreaterThanOrEqualTo(String value) {
            addCriterion("pub_scene >=", value, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneLessThan(String value) {
            addCriterion("pub_scene <", value, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneLessThanOrEqualTo(String value) {
            addCriterion("pub_scene <=", value, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneLike(String value) {
            addCriterion("pub_scene like", value, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneNotLike(String value) {
            addCriterion("pub_scene not like", value, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneIn(List<String> values) {
            addCriterion("pub_scene in", values, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneNotIn(List<String> values) {
            addCriterion("pub_scene not in", values, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneBetween(String value1, String value2) {
            addCriterion("pub_scene between", value1, value2, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubSceneNotBetween(String value1, String value2) {
            addCriterion("pub_scene not between", value1, value2, "pubScene");
            return (Criteria) this;
        }

        public Criteria andPubIntroIsNull() {
            addCriterion("pub_intro is null");
            return (Criteria) this;
        }

        public Criteria andPubIntroIsNotNull() {
            addCriterion("pub_intro is not null");
            return (Criteria) this;
        }

        public Criteria andPubIntroEqualTo(String value) {
            addCriterion("pub_intro =", value, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroNotEqualTo(String value) {
            addCriterion("pub_intro <>", value, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroGreaterThan(String value) {
            addCriterion("pub_intro >", value, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroGreaterThanOrEqualTo(String value) {
            addCriterion("pub_intro >=", value, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroLessThan(String value) {
            addCriterion("pub_intro <", value, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroLessThanOrEqualTo(String value) {
            addCriterion("pub_intro <=", value, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroLike(String value) {
            addCriterion("pub_intro like", value, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroNotLike(String value) {
            addCriterion("pub_intro not like", value, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroIn(List<String> values) {
            addCriterion("pub_intro in", values, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroNotIn(List<String> values) {
            addCriterion("pub_intro not in", values, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroBetween(String value1, String value2) {
            addCriterion("pub_intro between", value1, value2, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubIntroNotBetween(String value1, String value2) {
            addCriterion("pub_intro not between", value1, value2, "pubIntro");
            return (Criteria) this;
        }

        public Criteria andPubDateIsNull() {
            addCriterion("pub_date is null");
            return (Criteria) this;
        }

        public Criteria andPubDateIsNotNull() {
            addCriterion("pub_date is not null");
            return (Criteria) this;
        }

        public Criteria andPubDateEqualTo(Date value) {
            addCriterion("pub_date =", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateNotEqualTo(Date value) {
            addCriterion("pub_date <>", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateGreaterThan(Date value) {
            addCriterion("pub_date >", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateGreaterThanOrEqualTo(Date value) {
            addCriterion("pub_date >=", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateLessThan(Date value) {
            addCriterion("pub_date <", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateLessThanOrEqualTo(Date value) {
            addCriterion("pub_date <=", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateIn(List<Date> values) {
            addCriterion("pub_date in", values, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateNotIn(List<Date> values) {
            addCriterion("pub_date not in", values, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateBetween(Date value1, Date value2) {
            addCriterion("pub_date between", value1, value2, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateNotBetween(Date value1, Date value2) {
            addCriterion("pub_date not between", value1, value2, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumIsNull() {
            addCriterion("pub_watchNum is null");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumIsNotNull() {
            addCriterion("pub_watchNum is not null");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumEqualTo(Integer value) {
            addCriterion("pub_watchNum =", value, "pubWatchnum");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumNotEqualTo(Integer value) {
            addCriterion("pub_watchNum <>", value, "pubWatchnum");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumGreaterThan(Integer value) {
            addCriterion("pub_watchNum >", value, "pubWatchnum");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumGreaterThanOrEqualTo(Integer value) {
            addCriterion("pub_watchNum >=", value, "pubWatchnum");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumLessThan(Integer value) {
            addCriterion("pub_watchNum <", value, "pubWatchnum");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumLessThanOrEqualTo(Integer value) {
            addCriterion("pub_watchNum <=", value, "pubWatchnum");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumIn(List<Integer> values) {
            addCriterion("pub_watchNum in", values, "pubWatchnum");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumNotIn(List<Integer> values) {
            addCriterion("pub_watchNum not in", values, "pubWatchnum");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumBetween(Integer value1, Integer value2) {
            addCriterion("pub_watchNum between", value1, value2, "pubWatchnum");
            return (Criteria) this;
        }

        public Criteria andPubWatchnumNotBetween(Integer value1, Integer value2) {
            addCriterion("pub_watchNum not between", value1, value2, "pubWatchnum");
            return (Criteria) this;
        }

        public Criteria andPubStateIsNull() {
            addCriterion("pub_state is null");
            return (Criteria) this;
        }

        public Criteria andPubStateIsNotNull() {
            addCriterion("pub_state is not null");
            return (Criteria) this;
        }

        public Criteria andPubStateEqualTo(Integer value) {
            addCriterion("pub_state =", value, "pubState");
            return (Criteria) this;
        }

        public Criteria andPubStateNotEqualTo(Integer value) {
            addCriterion("pub_state <>", value, "pubState");
            return (Criteria) this;
        }

        public Criteria andPubStateGreaterThan(Integer value) {
            addCriterion("pub_state >", value, "pubState");
            return (Criteria) this;
        }

        public Criteria andPubStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("pub_state >=", value, "pubState");
            return (Criteria) this;
        }

        public Criteria andPubStateLessThan(Integer value) {
            addCriterion("pub_state <", value, "pubState");
            return (Criteria) this;
        }

        public Criteria andPubStateLessThanOrEqualTo(Integer value) {
            addCriterion("pub_state <=", value, "pubState");
            return (Criteria) this;
        }

        public Criteria andPubStateIn(List<Integer> values) {
            addCriterion("pub_state in", values, "pubState");
            return (Criteria) this;
        }

        public Criteria andPubStateNotIn(List<Integer> values) {
            addCriterion("pub_state not in", values, "pubState");
            return (Criteria) this;
        }

        public Criteria andPubStateBetween(Integer value1, Integer value2) {
            addCriterion("pub_state between", value1, value2, "pubState");
            return (Criteria) this;
        }

        public Criteria andPubStateNotBetween(Integer value1, Integer value2) {
            addCriterion("pub_state not between", value1, value2, "pubState");
            return (Criteria) this;
        }

        public Criteria andPubUserIsNull() {
            addCriterion("pub_user is null");
            return (Criteria) this;
        }

        public Criteria andPubUserIsNotNull() {
            addCriterion("pub_user is not null");
            return (Criteria) this;
        }

        public Criteria andPubUserEqualTo(String value) {
            addCriterion("pub_user =", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserNotEqualTo(String value) {
            addCriterion("pub_user <>", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserGreaterThan(String value) {
            addCriterion("pub_user >", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserGreaterThanOrEqualTo(String value) {
            addCriterion("pub_user >=", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserLessThan(String value) {
            addCriterion("pub_user <", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserLessThanOrEqualTo(String value) {
            addCriterion("pub_user <=", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserLike(String value) {
            addCriterion("pub_user like", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserNotLike(String value) {
            addCriterion("pub_user not like", value, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserIn(List<String> values) {
            addCriterion("pub_user in", values, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserNotIn(List<String> values) {
            addCriterion("pub_user not in", values, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserBetween(String value1, String value2) {
            addCriterion("pub_user between", value1, value2, "pubUser");
            return (Criteria) this;
        }

        public Criteria andPubUserNotBetween(String value1, String value2) {
            addCriterion("pub_user not between", value1, value2, "pubUser");
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