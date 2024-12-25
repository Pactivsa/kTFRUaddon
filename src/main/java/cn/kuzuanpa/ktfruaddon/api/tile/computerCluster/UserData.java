/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */
package cn.kuzuanpa.ktfruaddon.api.tile.computerCluster;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserData {
    public IComputerClusterUser user;
    public byte state;
    public short lastUpdated = 0;
    public Map<ComputePower, Long> consumingPower = new HashMap<>();
    public boolean needToSendToClient = false;
    public UserData(IComputerClusterUser user){this.user = user;}

    public UserData copy() {
        UserData data = new UserData(user);
        data.state=this.state;
        data.consumingPower =this.consumingPower;
        return data;
    }

    public static byte[] serialize(UserData data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes;
        try (DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeByte(data.state);
            dos.writeByte(data.consumingPower.size());
            for (Map.Entry<ComputePower, Long> entry : data.consumingPower.entrySet()) {
                ComputePower key = entry.getKey();
                Long amount = entry.getValue();
                dos.writeByte(key.ordinal());
                dos.writeLong(amount);
            }
            dos.flush();
            bytes = baos.toByteArray();
        }
        baos.close();

        return bytes;
    }

    public static UserData deserialize(byte [] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);
        byte state = dis.readByte();
        byte length = dis.readByte();
        Map<ComputePower, Long> consumingPower = new HashMap<>();
        for (int i = 0; i < length; i++) {
            consumingPower.put(ComputePower.getType(dis.readByte()), dis.readLong());
        }

        dis.close();
        bais.close();
        UserData data = new UserData(null);
        data.state=state;
        data.consumingPower = consumingPower;
        return data;
    }
}
