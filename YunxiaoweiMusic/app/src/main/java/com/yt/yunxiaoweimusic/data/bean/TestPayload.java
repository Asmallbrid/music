package com.yt.yunxiaoweimusic.data.bean;

import java.util.List;

public class TestPayload {
    public SemanticMeta semanticMeta;
    public Skill skill;
    public int code;

    public SemanticMeta getSemanticMeta() {
        return semanticMeta;
    }

    public void setSemanticMeta(SemanticMeta semanticMeta) {
        this.semanticMeta = semanticMeta;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class SemanticMeta {
        public int code;
        public Semantic semantic;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Semantic getSemantic() {
            return semantic;
        }

        public void setSemantic(Semantic semantic) {
            this.semantic = semantic;
        }
    }

    public static class Semantic {
        public String query;
        public String domain;
        public String intent;
        public List<Slot> slots;
        public boolean sessionComplete;
        public String skillId;
        public Ext mExt;
        public boolean sessionReject;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public List<Slot> getSlots() {
            return slots;
        }

        public void setSlots(List<Slot> slots) {
            this.slots = slots;
        }

        public boolean isSessionComplete() {
            return sessionComplete;
        }

        public void setSessionComplete(boolean sessionComplete) {
            this.sessionComplete = sessionComplete;
        }

        public String getSkillId() {
            return skillId;
        }

        public void setSkillId(String skillId) {
            this.skillId = skillId;
        }

        public Ext getmExt() {
            return mExt;
        }

        public void setmExt(Ext mExt) {
            this.mExt = mExt;
        }

        public boolean isSessionReject() {
            return sessionReject;
        }

        public void setSessionReject(boolean sessionReject) {
            this.sessionReject = sessionReject;
        }
    }

    public static class Slot {
        public String name;
        public String type;
        public int slotStruct;
        public List<Value> values;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSlotStruct() {
            return slotStruct;
        }

        public void setSlotStruct(int slotStruct) {
            this.slotStruct = slotStruct;
        }

        public List<Value> getValues() {
            return values;
        }

        public void setValues(List<Value> values) {
            this.values = values;
        }
    }

    public class Ext {
        public String can_enter_duplex;

        public String getCan_enter_duplex() {
            return can_enter_duplex;
        }

        public void setCan_enter_duplex(String can_enter_duplex) {
            this.can_enter_duplex = can_enter_duplex;
        }
    }

    public static class Value {
        public String text;
        public String originalText;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getOriginalText() {
            return originalText;
        }

        public void setOriginalText(String originalText) {
            this.originalText = originalText;
        }
    }

    public class Skill {
        public String speakTipsText;
        public String tipsText;
        public String oldSpeakText;
        public String oldResponseText;
        public String data;

        public String getSpeakTipsText() {
            return speakTipsText;
        }

        public void setSpeakTipsText(String speakTipsText) {
            this.speakTipsText = speakTipsText;
        }

        public String getTipsText() {
            return tipsText;
        }

        public void setTipsText(String tipsText) {
            this.tipsText = tipsText;
        }

        public String getOldSpeakText() {
            return oldSpeakText;
        }

        public void setOldSpeakText(String oldSpeakText) {
            this.oldSpeakText = oldSpeakText;
        }

        public String getOldResponseText() {
            return oldResponseText;
        }

        public void setOldResponseText(String oldResponseText) {
            this.oldResponseText = oldResponseText;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
