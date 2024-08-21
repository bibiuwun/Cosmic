/*
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

/*
   @Author: clem
*/
package client.command.commands.gm4;

import client.Character;
import client.Client;
import client.command.Command;
import client.inventory.Equip;
import client.inventory.InventoryType;
import client.inventory.Item;
import client.inventory.manipulator.InventoryManipulator;
import constants.inventory.ItemConstants;
import server.ItemInformationProvider;
import java.lang.reflect.Method;

public class SpecificProItemCommand extends Command {
    {
        setDescription("Spawn an item with specific custom stats.");
    }

    @Override
    public void execute(Client c, String[] params) {
        Character player = c.getPlayer();
        if (params.length < 1 || params.length > 15) {
            player.yellowMessage("Syntax: !proitem <itemid> [<str>] [<dex>] [<int>] [<luk>] [<slots>] [<watk>] [<matk>] [<acc>] [<avd>] [<spd>] [<jmp>] [<wdef>] [<mdef>] [<hp>] [<mp>]");
            return;
        }

        ItemInformationProvider ii = ItemInformationProvider.getInstance();
        int itemid = Integer.parseInt(params[0]);

        if (ii.getName(itemid) == null) {
            player.yellowMessage("Item id '" + params[0] + "' does not exist.");
            return;
        }

        InventoryType type = ItemConstants.getInventoryType(itemid);
        if (type.equals(InventoryType.EQUIP)) {
            Item it = ii.getEquipById(itemid);
            it.setOwner(player.getName());

            String[] methods = {"setStr", "setDex", "setInt", "setLuk", "setUpgradeSlots", "setWatk", "setMatk", "setAcc", "setAvoid", "setSpeed", "setJump", "setWdef", "setMdef", "setHp", "setMp"};

            for (int i = 1; i < params.length; i++) {
                try {
                    if (i == 5) {
                        Method method = it.getClass().getMethod(methods[i-1], byte.class);

                        byte stat = (byte) Math.max(0, parseByteOrDefault(params[i], (byte) 0));

                        method.invoke(it, stat);
                    } else {
                        Method method = it.getClass().getMethod(methods[i-1], short.class);

                        short stat = (short) Math.max(0, parseShortOrDefault(params[i], (short) 0));

                        method.invoke(it, stat);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            short flag = it.getFlag();
            flag |= ItemConstants.UNTRADEABLE;
            it.setFlag(flag);

            InventoryManipulator.addFromDrop(c, it);
        } else {
            player.dropMessage(6, "Make sure it's an equippable item.");
        }

    }
    private static short parseShortOrDefault(String str, short defaultValue) {
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException e) {
            // If the string is not numeric, return the default value
            return defaultValue;
        }
    }

    private static byte parseByteOrDefault(String str, byte defaultValue) {
        try {
            return Byte.parseByte(str);
        } catch (NumberFormatException e) {
            // If the string is not numeric, return the default value
            return defaultValue;
        }
    }
}
