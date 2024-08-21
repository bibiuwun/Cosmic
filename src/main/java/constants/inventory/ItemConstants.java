/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package constants.inventory;

import client.inventory.InventoryType;
import config.YamlConfig;
import constants.id.ItemId;

import java.util.*;

/**
 * @author Jay Estrella
 * @author Ronan
 */
public final class ItemConstants {
    protected static Map<Integer, InventoryType> inventoryTypeCache = new HashMap<>();

    public final static short LOCK = 0x01;
    public final static short SPIKES = 0x02;
    public final static short KARMA_USE = 0x02;
    public final static short COLD = 0x04;
    public final static short UNTRADEABLE = 0x08;
    public final static short KARMA_EQP = 0x10;
    public final static short SANDBOX = 0x40;             // let 0x40 until it's proven something uses this
    public final static short PET_COME = 0x80;
    public final static short ACCOUNT_SHARING = 0x100;
    public final static short MERGE_UNTRADEABLE = 0x200;

    public final static boolean EXPIRING_ITEMS = true;
    public final static Set<Integer> permanentItemids = new HashSet<>();

    static {
        // i ain't going to open one gigantic itemid cache just for 4 perma itemids, no way!
        for (int petItemId : ItemId.getPermaPets()) {
            permanentItemids.add(petItemId);
        }
    }

    public static int getFlagByInt(int type) {
        if (type == 128) {
            return PET_COME;
        } else if (type == 256) {
            return ACCOUNT_SHARING;
        }
        return 0;
    }
    
    public static boolean isPercentagePot(int itemId){
        List<Integer> pots = Arrays.asList(2000004, 2000012, 2002015, 2002020, 2002021, 2002022, 2002023, 2002026, 2010005, 2010006, 2020011, 2020031, 2022007, 2022008, 2022009, 2022010, 2022011, 2022015, 2022028, 2022029, 2022105, 2022161, 2022179, 2022195, 2022242, 2022244, 2022431, 2022456, 2022175);
        return pots.contains((Integer)itemId);
    }

    public static boolean isThrowingStar(int itemId) {
        return itemId / 10000 == 207;
    }

    public static boolean isBullet(int itemId) {
        return itemId / 10000 == 233;
    }

    public static boolean isPotion(int itemId) {
        return itemId / 1000 == 2000;
    }

    public static boolean isFood(int itemId) {
        int useType = itemId / 1000;
        return useType == 2022 || useType == 2010 || useType == 2020;
    }

    public static boolean isHammerEnabled(int itemId) {
        return itemId != 1082399; // super scg
    }

    public static boolean isConsumable(int itemId) {
        return isPotion(itemId) || isFood(itemId);
    }

    public static boolean isRechargeable(int itemId) {
        return isThrowingStar(itemId) || isBullet(itemId);
    }

    public static boolean isArrowForCrossBow(int itemId) {
        return itemId / 1000 == 2061 || itemId == 02060005 || itemId == 02060006;
    }

    public static boolean isArrowForBow(int itemId) {
        return itemId / 1000 == 2060;
    }

    public static boolean isArrow(int itemId) {
        return isArrowForBow(itemId) || isArrowForCrossBow(itemId);
    }

    public static boolean isPet(int itemId) {
        //return itemId / 1000 == 5000;
        return itemId >= 5000000 && itemId < 5003000;
    }

    public static boolean isExpirablePet(int itemId) {
        return YamlConfig.config.server.USE_ERASE_PET_ON_EXPIRATION || itemId == 5000054;
    }

    public static boolean isPermanentItem(int itemId) {
        return permanentItemids.contains(itemId);
    }

    public static boolean isNewYearCardEtc(int itemId) {
        return itemId / 10000 == 430;
    }

    public static boolean isNewYearCardUse(int itemId) {
        return itemId / 10000 == 216;
    }

    public static boolean isAccessory(int itemId) {
        return itemId >= 1110000 && itemId < 1145000;
    }

    public static boolean isTaming(int itemId) {
        int itemType = itemId / 1000;
        return itemType == 1902 || itemType == 1912;
    }

    public static boolean isTownScroll(int itemId) {
        return itemId >= 2030000 && itemId < 2030100;
    }
    
    public static boolean isAntibanishScroll(int itemId) {
        return itemId == 2030100;
    }
    
    public static boolean isCleanSlate(int scrollId) {
        return scrollId > 2048999 && scrollId < 2049004;
    }

    public static boolean isResetScroll(int scrollId) {
        return scrollId == 2049115 || scrollId == 2049117;
    }

    public static boolean isModifierScroll(int scrollId) {
        return scrollId == 2040727 || scrollId == 2041058;
    }
    
    public static boolean isFlagModifier(int scrollId, short flag) {
        if (scrollId == ItemId.COLD_PROTECTION_SCROLl && ((flag & ItemConstants.COLD) == ItemConstants.COLD)) {
            return true;
        }
        return scrollId == ItemId.SPIKES_SCROLL && ((flag & ItemConstants.SPIKES) == ItemConstants.SPIKES);
    }

    public static boolean isChaosScroll(int scrollId) {
    	return scrollId >= 2049100 && scrollId <= 2049103;
    }

    public static boolean isWitchChaosScroll(int scrollId) {
        return scrollId == 2049114;
    }

    public static boolean isWitchBelt(int itemid) {
        return itemid >= 1132014 && itemid <= 1132016;
    }

    // use this for blocking cash shop items
    public static boolean isRateCoupon(int itemId) {
        int itemType = itemId / 1000;
        return itemType == 5211 || itemType == 5360 || itemType == 5570 || itemType == 5220 || itemType == 5451
                || itemType == 5450 || itemType == 5520 || itemType == 5050 || itemType == 5510 || itemType == 5130;
    }

    public static boolean isExpCoupon(int couponId) {
        return couponId / 1000 == 5211;
    }

    public static boolean isPartyItem(int itemId) {
        return itemId >= 2022430 && itemId <= 2022433 || itemId >= 2022160 && itemId <= 2022163;
    }
    
    public static boolean isPartyAllcure(int itemId) {
        return itemId == 2022433 || itemId == 2022163;
    }
    
    public static boolean isHiredMerchant(int itemId) {
        return itemId / 10000 == 503;
    }

    public static boolean isPlayerShop(int itemId) {
        return itemId / 10000 == 514;
    }

    public static InventoryType getInventoryType(final int itemId) {
        if (inventoryTypeCache.containsKey(itemId)) {
            return inventoryTypeCache.get(itemId);
        }

        InventoryType ret = InventoryType.UNDEFINED;

        final byte type = (byte) (itemId / 1000000);
        if (type >= 1 && type <= 5) {
            ret = InventoryType.getByType(type);
        }

        inventoryTypeCache.put(itemId, ret);
        return ret;
    }

    public static boolean isMakerReagent(int itemId) {
        return itemId / 10000 == 425;
    }

    public static boolean isOverall(int itemId) {
        return itemId / 10000 == 105;
    }

    public static boolean isCashStore(int itemId) {
        int itemType = itemId / 10000;
        return itemType == 503 || itemType == 514;
    }

    public static boolean isMapleLife(int itemId) {
        int itemType = itemId / 10000;
        return itemType == 543 && itemId != 5430000;
    }

    public static boolean isWeapon(int itemId) {
        return itemId >= 1302000 && itemId < 1493000;
    }

    public static boolean isEquipment(int itemId) {
        return itemId < 2000000 && itemId != 0;
    }

    public static boolean isFishingChair(int itemId) {
        return itemId == ItemId.FISHING_CHAIR;
    }

    public static boolean isMedal(int itemId) {
        return itemId >= 1140000 && itemId < 1145000;
    }
    
    public static boolean isWeddingRing(int itemId) {
        return itemId >= 1112803 && itemId <= 1112809;
    }
    
    public static boolean isWeddingToken(int itemId) {
        return itemId >= 4031357 && itemId <= 4031364;
    }
    
    public static boolean isFace(int itemId) {
        return ((itemId >= 20000 && itemId < 30000));// || (itemId >= 50000 && itemId < 99999));
    }
    
    public static boolean isHair(int itemId) {
        return ((itemId >= 30000 && itemId < 50000) || (itemId >= 50000 && itemId < 69999));
    }
    
    public static boolean isFaceExpression(int itemId) {
        return itemId / 10000 == 516;
    }
    
    public static boolean isChair(int itemId) {
        return itemId / 10000 == 301;
    }

    public static boolean isTimelessWeapon(int itemid) {
        return itemid == 1302081 ||
                itemid == 1312037 ||
                itemid == 1322060 ||
                itemid == 1402046 ||
                itemid == 1412033 ||
                itemid == 1422037 ||
                itemid == 1442063 ||
                itemid == 1482023 ||
                itemid == 1332073 ||
                itemid == 1332074 ||
                itemid == 1372044 ||
                itemid == 1382057 ||
                itemid == 1432047 ||
                itemid == 1452057 ||
                itemid == 1462050 ||
                itemid == 1472068 ||
                itemid == 1304011 || /*Timeless Grim Seeker*/
                itemid == 1305011 || /*Timeless Hefty Head*/
                itemid == 1333011 || /*Timeless Chain*/
                itemid == 1335011 || /*Reverse Ritual Fan*/
                itemid == 1335012 || /*Timeless Ritual Fan*/
                itemid == 1373011 || /*Timeless Kitty Pride Scepter*/
                itemid == 1376013 || /*Timeless Fan of Altruism*/
                itemid == 1384011 || /*Timeless Lucent Gauntlet*/
                itemid == 1383011 || /*Timeless Dead End*/
                itemid == 1454012 || /*Timeless Ancient Bow*/
                itemid == 1483010 || /*Timeless Jager*/
                itemid == 1492023;
    }

    public static boolean isReverseWeapon(int itemid) {
        return itemid == 1302086 ||
                itemid == 1312038 ||
                itemid == 1322061 ||
                itemid == 1332075 ||
                itemid == 1332076 ||
                itemid == 1372045 ||
                itemid == 1382059 ||
                itemid == 1402047 ||
                itemid == 1412034 ||
                itemid == 1422038 ||
                itemid == 1432049 ||
                itemid == 1442067 ||
                itemid == 1452059 ||
                itemid == 1462051 ||
                itemid == 1472071 ||
                itemid == 1482024 ||
                itemid == 1383012 || /*Reverse Dead End*/
                itemid == 1454011 || /*Reverse Ancient Bow*/
                itemid == 1376012 || /*Reverse Fan of Altruism*/
                itemid == 1384010 || /*Reverse Lucent Gauntlet*/
                itemid == 1304012 || /*Reverse Grim Seeker*/
                itemid == 1305012 || /*Reverse Hefty Head*/
                itemid == 1333010 || /*Reverse Chain*/
                itemid == 1373012 || /*Reverse Kitty Pride Scepter*/
                itemid == 1483009 || /*Reverse Surtr*/
                itemid == 1492025;
    }

}
