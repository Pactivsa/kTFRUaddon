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
package cn.kuzuanpa.ktfruaddon.code;

import java.util.Map;

public class SingleEntry<K, V> implements Map.Entry<K, V> {
    K key;
    V value;

    public SingleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public V setValue(V value) {
        V ret = this.value;
        this.value = value;
        return ret;
    }

    public boolean equals(Object paramObject) {
        if (!(paramObject instanceof SingleEntry)) {
            return super.equals(paramObject);
        } else {
            return ((SingleEntry<?, ?>)paramObject).getKey().equals(this.getKey()) && ((SingleEntry<?, ?>)paramObject).getValue().equals(this.getValue());
        }
    }
}
