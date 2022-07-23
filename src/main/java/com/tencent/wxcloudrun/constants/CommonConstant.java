package com.tencent.wxcloudrun.constants;

/**
 * @author wuzzi
 */
public interface CommonConstant {

    int REWARD_COUNT = 3;
    float REWARD_MONEY = 198;

    interface Flag {
        int NO = 0;
        int YES = 1;
    }

    interface VipFlag {
        int NORMAL_USER = 0;
        int VIP = 1;
        int SUPER_VIP = 2;
    }

    interface AccountChangeType {
        int RECOMMEND_AWARD = 0;
        int CASH = 1;
    }

    interface AccountChangeStatus {
        int VALID = 0;
        int INVALID = 1;
        int GOING = 2;
    }

}
