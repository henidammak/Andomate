package com.kam.andromate.model.androidStageModel;

import com.kam.andromate.IConstants;
import com.kam.andromate.model.AndroidStage;
import com.kam.andromate.model.PipelineStage;

import org.json.JSONObject;

public class IntentStage extends AndroidStage {

    public final static String TAG_INTENT_ACTION = "Action";
    public final static String TAG_INTENT_PACKAGE_NAME = "PackageName";
    public final static String TAG_INTENT_CLASS_NAME = "ClassName";
    public final static String TAG_INTENT_DATA = "Data";
    public final static String TAG_INTENT_ACTION_TYPE = "ActionType";
    
    
    
    public final static String DEFAULT_INTENT_ACTION = IConstants.EMPTY_STRING;
    public final static String DEFAULT_INTENT_PACKAGE_NAME = IConstants.EMPTY_STRING;
    public final static String DEFAULT_INTENT_CLASS_NAME = IConstants.EMPTY_STRING;
    public final static String DEFAULT_INTENT_DATA = IConstants.EMPTY_STRING;
    public final static String DEFAULT_INTENT_ACTION_TYPE = IConstants.EMPTY_STRING;
    
    
    
    private String intentAction;
    private String intentPackageName;
    private String intentClassName;
    private String intentData;
    private String intentActionType;

    public IntentStage(String intentAction, String intentPackageName, String intentClassName, String intentData, String intentActionType) {
        this.intentAction = intentAction;
        this.intentPackageName = intentPackageName;
        this.intentClassName = intentClassName;
        this.intentData = intentData;
        this.intentActionType = intentActionType;
    }
    
    public String getIntentAction() {
        return intentAction;
    }

    public void setIntentAction(String intentAction) {
        this.intentAction = intentAction;
    }

    public String getIntentPackageName() {
        return intentPackageName;
    }

    public void setIntentPackageName(String intentPackageName) {
        this.intentPackageName = intentPackageName;
    }

    public String getIntentClassName() {
        return intentClassName;
    }

    public void setIntentClassName(String intentClassName) {
        this.intentClassName = intentClassName;
    }

    public String getIntentData() {
        return intentData;
    }

    public void setIntentData(String intentData) {
        this.intentData = intentData;
    }

    public String getIntentActionType() {
        return intentActionType;
    }

    public void setIntentActionType(String intentActionType) {
        this.intentActionType = intentActionType;
    }

    @Override
    public PipelineStage jsonToPipeLine(JSONObject jo) {
        return new IntentStage(
                jo.optString(TAG_INTENT_ACTION, DEFAULT_INTENT_ACTION),
                jo.optString(TAG_INTENT_PACKAGE_NAME, DEFAULT_INTENT_PACKAGE_NAME),
                jo.optString(TAG_INTENT_CLASS_NAME, DEFAULT_INTENT_CLASS_NAME),
                jo.optString(TAG_INTENT_DATA, DEFAULT_INTENT_DATA),
                jo.optString(TAG_INTENT_ACTION_TYPE, DEFAULT_INTENT_ACTION_TYPE)
        );
    }
    
    
}
