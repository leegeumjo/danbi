package game;

import io.netty.channel.ChannelHandlerContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Logger;

import network.Handler;
import packet.Packet;
import database.DataBase;
import database.GameData;

import javax.xml.transform.Result;

public class User {
	
	private Logger logger = Logger.getLogger(User.class.getName());
	private ChannelHandlerContext ctx;
	private int no;
	private String id;
	private String pass;
	private String name;
	private String mail;
	private String image;
	private String guild;
	private int job;
	private int pureStr;
	private int pureDex;
	private int pureAgi;
	private int statPoint;
	private int skillPoint;
	private int title;
	private int hp;
	private int mp;
	private int level;
	private int exp;
	private int gold;
	private int map;
	private int x;
	private int y;
	private int direction;
	private int speed;
	private boolean admin;
	
	private int weapon = 0;
	private int shield = 0;
	private int helmet = 0;
	private int armor = 0;
	private int cape = 0;
	private int shoes = 0;
	private int accessory = 0;

	private int maxInventory = 35;
	
	private Hashtable<Integer, GameData.InventoryItem> inventory = new Hashtable<Integer, GameData.InventoryItem>();
	
	public User(ChannelHandlerContext ctx, ResultSet rs) throws SQLException {
		this.ctx = ctx;
		this.no = rs.getInt("no");
		this.id = rs.getString("id");
		this.pass = rs.getString("pass");
		this.name = rs.getString("name");
		this.title = rs.getInt("title");
		this.guild = "";
		this.mail = rs.getString("mail");
		this.image = rs.getString("image");
		this.job = rs.getInt("job");
		this.pureStr = rs.getInt("str");
		this.pureDex = rs.getInt("dex");
		this.pureAgi = rs.getInt("agi");
		this.statPoint = rs.getInt("stat_point");
		this.skillPoint = rs.getInt("skill_point");
		this.hp = rs.getInt("hp");
		this.mp = rs.getInt("mp");
		this.level = rs.getInt("level");
		this.exp = rs.getInt("exp");
		this.map = rs.getInt("map");
		this.gold = rs.getInt("gold");
		this.x = rs.getInt("x");
		this.y = rs.getInt("y");
		this.direction = rs.getInt("direction");
		this.speed = rs.getInt("speed");
		this.admin = rs.getInt("admin") ==0 ? false : true;
	}
	
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	
	public int getNo() {
		return no;
	}
	
	public String getID() {
		return id;
	}
	
	public String getPass() {
		return pass;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTitle() {
		return title;
	}

	public void setTitle(int t) {
		title = t;
		ctx.writeAndFlush(Packet.updateStatus(GameData.StatusType.TITLE, t));
	}
	
	public String getGuild() {
		return guild;
	}
	
	public String getMail() {
		return mail;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String i) {
		image = i;
		ctx.writeAndFlush(Packet.updateStatus(GameData.StatusType.IMAGE, i));
	}
	
	public int getJob() {
		return job;
	}

	public void setJob(int j) {
		job = j;
		ctx.writeAndFlush(Packet.updateStatus(GameData.StatusType.JOB, j));
	}
	
	public int getStr() {
		int n = 0;
		// 스텟 Str
		n += pureStr;
		// 직업 기본 Str
		n += GameData.job.get(job).getStr() * level;
		// 아이템으로 오르는 Str
		if (weapon > 0) {
			n += GameData.item.get(findItemByIndex(weapon).getItemNo()).getStr();
			n += findItemByIndex(weapon).getStr();
		}
		if (shield > 0) {
			n += GameData.item.get(findItemByIndex(shield).getItemNo()).getStr();
			n += findItemByIndex(shield).getStr();
		}
		if (helmet > 0) {
			n += GameData.item.get(findItemByIndex(helmet).getItemNo()).getStr();
			n += findItemByIndex(helmet).getStr();
		}
		if (armor > 0) {
			n += GameData.item.get(findItemByIndex(armor).getItemNo()).getStr();
			n += findItemByIndex(armor).getStr();
		}
		if (cape > 0) {
			n += GameData.item.get(findItemByIndex(cape).getItemNo()).getStr();
			n += findItemByIndex(cape).getStr();
		}
		if (shoes > 0) {
			n += GameData.item.get(findItemByIndex(shoes).getItemNo()).getStr();
			n += findItemByIndex(shoes).getStr();
		}
		if (accessory > 0) {
			n += GameData.item.get(findItemByIndex(accessory).getItemNo()).getStr();
			n += findItemByIndex(accessory).getStr();
		}

		return n;
	}
	
	public int getPureStr() {
		return pureStr;
	}
	
	public int getDex() {
		int n = 0;
		// 스텟 Dex
		n += pureDex;
		// 직업 기본 Dex
		n += GameData.job.get(job).getDex() * level;
		// 아이템으로 오르는 Dex
		if (weapon > 0) {
			n += GameData.item.get(findItemByIndex(weapon).getItemNo()).getDex();
			n += findItemByIndex(weapon).getDex();
		}
		if (shield > 0) {
			n += GameData.item.get(findItemByIndex(shield).getItemNo()).getDex();
			n += findItemByIndex(shield).getDex();
		}
		if (helmet > 0) {
			n += GameData.item.get(findItemByIndex(helmet).getItemNo()).getDex();
			n += findItemByIndex(helmet).getDex();
		}
		if (armor > 0) {
			n += GameData.item.get(findItemByIndex(armor).getItemNo()).getDex();
			n += findItemByIndex(armor).getDex();
		}
		if (cape > 0) {
			n += GameData.item.get(findItemByIndex(cape).getItemNo()).getDex();
			n += findItemByIndex(cape).getDex();
		}
		if (shoes > 0) {
			n += GameData.item.get(findItemByIndex(shoes).getItemNo()).getDex();
			n += findItemByIndex(shoes).getDex();
		}
		if (accessory > 0) {
			n += GameData.item.get(findItemByIndex(accessory).getItemNo()).getDex();
			n += findItemByIndex(accessory).getDex();
		}
		return n;
	}
	
	public int getPureDex() {
		return pureDex;
	}
	
	public int getAgi() {
		int n = 0;
		// 스텟 Agi
		n += pureAgi;
		// 직업 기본 Agi
		n += GameData.job.get(job).getAgi() * level;
		// 아이템으로 오르는 Agi
		if (weapon > 0) {
			n += GameData.item.get(findItemByIndex(weapon).getItemNo()).getAgi();
			n += findItemByIndex(weapon).getAgi();
		}
		if (shield > 0) {
			n += GameData.item.get(findItemByIndex(shield).getItemNo()).getAgi();
			n += findItemByIndex(shield).getAgi();
		}
		if (helmet > 0) {
			n += GameData.item.get(findItemByIndex(helmet).getItemNo()).getAgi();
			n += findItemByIndex(helmet).getAgi();
		}
		if (armor > 0) {
			n += GameData.item.get(findItemByIndex(armor).getItemNo()).getAgi();
			n += findItemByIndex(armor).getAgi();
		}
		if (cape > 0) {
			n += GameData.item.get(findItemByIndex(cape).getItemNo()).getAgi();
			n += findItemByIndex(cape).getAgi();
		}
		if (shoes > 0) {
			n += GameData.item.get(findItemByIndex(shoes).getItemNo()).getAgi();
			n += findItemByIndex(shoes).getAgi();
		}
		if (accessory > 0) {
			n += GameData.item.get(findItemByIndex(accessory).getItemNo()).getAgi();
			n += findItemByIndex(accessory).getAgi();
		}
		return n;
	}
	
	public int getPureAgi() {
		return pureAgi;
	}

	public int getHp() {
		return hp;
	}

	public void gainHp(int value) {
		if (hp + value > getMaxHp())
			value = getMaxHp() - hp;

		hp += value;
		ctx.writeAndFlush(Packet.updateStatus(GameData.StatusType.HP, hp));
	}

	public void loseHp(int value) {
		if (hp - value < 0) {
			// 쥬금
			return;
		}

		gainHp(-value);
	}
	
	public int getMaxHp() {
		int n = 0;
		// 직업 기본 Hp
		n += GameData.job.get(job).getHp() * level;
		// 아이템으로 오르는 Hp
		if (weapon > 0) {
			n += GameData.item.get(findItemByIndex(weapon).getItemNo()).getHp();
			n += findItemByIndex(weapon).getHp();
		}
		if (shield > 0) {
			n += GameData.item.get(findItemByIndex(shield).getItemNo()).getHp();
			n += findItemByIndex(shield).getHp();
		}
		if (helmet > 0) {
			n += GameData.item.get(findItemByIndex(helmet).getItemNo()).getHp();
			n += findItemByIndex(helmet).getHp();
		}
		if (armor > 0) {
			n += GameData.item.get(findItemByIndex(armor).getItemNo()).getHp();
			n += findItemByIndex(armor).getHp();
		}
		if (cape > 0) {
			n += GameData.item.get(findItemByIndex(cape).getItemNo()).getHp();
			n += findItemByIndex(cape).getHp();
		}
		if (shoes > 0) {
			n += GameData.item.get(findItemByIndex(shoes).getItemNo()).getHp();
			n += findItemByIndex(shoes).getHp();
		}
		if (accessory > 0) {
			n += GameData.item.get(findItemByIndex(accessory).getItemNo()).getHp();
			n += findItemByIndex(accessory).getHp();
		}

		return n;
	}

	public int getMp() {
		return mp;
	}

	public void gainMp(int value) {
		if (mp + value > getMaxMp())
			value = getMaxMp() - mp;

		mp += value;
		ctx.writeAndFlush(Packet.updateStatus(GameData.StatusType.MP, mp));
	}

	public boolean loseMp(int value) {
		if (mp - value < 0)
			return false;

		gainMp(-value);
		return true;
	}
	
	public int getMaxMp() {
		int n = 0;
		// 직업 기본 Mp
		n += GameData.job.get(job).getMp() * level;
		// 아이템으로 오르는 Mp
		if (weapon > 0) {
			n += GameData.item.get(findItemByIndex(weapon).getItemNo()).getMp();
			n += findItemByIndex(weapon).getMp();
		}
		if (shield > 0) {
			n += GameData.item.get(findItemByIndex(shield).getItemNo()).getMp();
			n += findItemByIndex(shield).getMp();
		}
		if (helmet > 0) {
			n += GameData.item.get(findItemByIndex(helmet).getItemNo()).getMp();
			n += findItemByIndex(helmet).getMp();
		}
		if (armor > 0) {
			n += GameData.item.get(findItemByIndex(armor).getItemNo()).getMp();
			n += findItemByIndex(armor).getMp();
		}
		if (cape > 0) {
			n += GameData.item.get(findItemByIndex(cape).getItemNo()).getMp();
			n += findItemByIndex(cape).getMp();
		}
		if (shoes > 0) {
			n += GameData.item.get(findItemByIndex(shoes).getItemNo()).getMp();
			n += findItemByIndex(shoes).getMp();
		}
		if (accessory > 0) {
			n += GameData.item.get(findItemByIndex(accessory).getItemNo()).getMp();
			n += findItemByIndex(accessory).getMp();
		}
		
		return n;
	}
	
	public int getCritical() {
		int n = 0;
		// 아이템으로 오르는 Critical
		if (weapon > 0) {
			n += GameData.item.get(findItemByIndex(weapon).getItemNo()).getCritical();
			n += findItemByIndex(weapon).getCritical();
		}
		if (shield > 0) {
			n += GameData.item.get(findItemByIndex(shield).getItemNo()).getCritical();
			n += findItemByIndex(shield).getCritical();
		}
		if (helmet > 0) {
			n += GameData.item.get(findItemByIndex(helmet).getItemNo()).getCritical();
			n += findItemByIndex(helmet).getCritical();
		}
		if (armor > 0) {
			n += GameData.item.get(findItemByIndex(armor).getItemNo()).getCritical();
			n += findItemByIndex(armor).getCritical();
		}
		if (cape > 0) {
			n += GameData.item.get(findItemByIndex(cape).getItemNo()).getCritical();
			n += findItemByIndex(cape).getCritical();
		}
		if (shoes > 0) {
			n += GameData.item.get(findItemByIndex(shoes).getItemNo()).getCritical();
			n += findItemByIndex(shoes).getCritical();
		}
		if (accessory > 0) {
			n += GameData.item.get(findItemByIndex(accessory).getItemNo()).getCritical();
			n += findItemByIndex(accessory).getCritical();
		}
		
		return n;
	}
	
	public int getAvoid() {
		int n = 0;
		// 아이템으로 오르는 Avoid
		if (weapon > 0) {
			n += GameData.item.get(findItemByIndex(weapon).getItemNo()).getAvoid();
			n += findItemByIndex(weapon).getAvoid();
		}
		if (shield > 0) {
			n += GameData.item.get(findItemByIndex(shield).getItemNo()).getAvoid();
			n += findItemByIndex(shield).getAvoid();
		}
		if (helmet > 0) {
			n += GameData.item.get(findItemByIndex(helmet).getItemNo()).getAvoid();
			n += findItemByIndex(helmet).getAvoid();
		}
		if (armor > 0) {
			n += GameData.item.get(findItemByIndex(armor).getItemNo()).getAvoid();
			n += findItemByIndex(armor).getAvoid();
		}
		if (cape > 0) {
			n += GameData.item.get(findItemByIndex(cape).getItemNo()).getAvoid();
			n += findItemByIndex(cape).getAvoid();
		}
		if (shoes > 0) {
			n += GameData.item.get(findItemByIndex(shoes).getItemNo()).getAvoid();
			n += findItemByIndex(shoes).getAvoid();
		}
		if (accessory > 0) {
			n += GameData.item.get(findItemByIndex(accessory).getItemNo()).getAvoid();
			n += findItemByIndex(accessory).getAvoid();
		}

		return n;
	}
	
	public int getHit() {
		int n = 0;
		// 아이템으로 오르는 Hit
		if (weapon > 0) {
			n += GameData.item.get(findItemByIndex(weapon).getItemNo()).getHit();
			n += findItemByIndex(weapon).getHit();
		}
		if (shield > 0) {
			n += GameData.item.get(findItemByIndex(shield).getItemNo()).getHit();
			n += findItemByIndex(shield).getHit();
		}
		if (helmet > 0) {
			n += GameData.item.get(findItemByIndex(helmet).getItemNo()).getHit();
			n += findItemByIndex(helmet).getHit();
		}
		if (armor > 0) {
			n += GameData.item.get(findItemByIndex(armor).getItemNo()).getHit();
			n += findItemByIndex(armor).getHit();
		}
		if (cape > 0) {
			n += GameData.item.get(findItemByIndex(cape).getItemNo()).getHit();
			n += findItemByIndex(cape).getHit();
		}
		if (shoes > 0) {
			n += GameData.item.get(findItemByIndex(shoes).getItemNo()).getHit();
			n += findItemByIndex(shoes).getHit();
		}
		if (accessory > 0) {
			n += GameData.item.get(findItemByIndex(accessory).getItemNo()).getHit();
			n += findItemByIndex(accessory).getHit();
		}


		return n;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getExp() {
		return exp;
	}

	public int getMaxExp() {
		int n = 0;
		n += level * level * 10;

		return n;
	}

	public void gainExp(int value) {
		int maxExp = getMaxExp();
		exp += value;

		if (exp >= maxExp) {
			exp = 0;
			level++;
			statPoint += 5;
			skillPoint += 1;

			ctx.write(Packet.updateStatus(GameData.StatusType.LEVEL, level));
			ctx.write(Packet.updateStatus(GameData.StatusType.STAT_POINT, statPoint));
			ctx.write(Packet.updateStatus(GameData.StatusType.SKILL_POINT, skillPoint));
			ctx.write(Packet.updateStatus(GameData.StatusType.MAX_EXP, getMaxExp()));
		}

		ctx.writeAndFlush(Packet.updateStatus(GameData.StatusType.EXP, exp));
	}

	public void loseExp(int value) {
		if (exp - value < 0)
			value = exp;

		gainExp(-value);
	}

	public int getGold() {
		return gold;
	}

	public void gainGold(int value) {
		gold += value;

		//
	}

	public boolean loseGold(int value) {
		if (gold < value)
			return false;

		gainGold(-value);
		return true;
	}
	
	public int getMap() {
		return map;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getStatPoint() {
		return statPoint;
	}
	
	public int getSkillPoint() {
		return skillPoint;
	}
	
	public boolean isAdmin() {
		return admin;
	}

	public int getWeapon() {
		return weapon;
	}
	
	public int getShield() {
		return shield;
	}
	
	public int getHelmet() {
		return helmet;
	}
	
	public int getArmor() {
		return armor;
	}
	
	public int getCape() {
		return cape;
	}
	
	public int getShoes() {
		return shoes;
	}
	
	public int getAccessory() {
		return accessory;
	}

	public int getMaxInventory() {
		return maxInventory;
	}
	
	public void loadData() {
		loadEquipItem();
		loadInventory();
	}
	
	// 장착한 아이템 불러오기
	public void loadEquipItem() {
		try {
			ResultSet rs = DataBase.executeQuery("SELECT * FROM `equip` WHERE `user_no` = '" + no + "';");
    	
	    	if (rs.next()) {
	    		weapon = rs.getInt("weapon");
	    		shield = rs.getInt("shield");
	    		helmet = rs.getInt("helmet");
	    		armor = rs.getInt("armor");
	    		cape = rs.getInt("cape");
	    		shoes = rs.getInt("shoes");
	    		accessory = rs.getInt("accessory");
	    	} else {
				DataBase.insertEquip(no);
			}
	    	
			rs.close();
		} catch (SQLException e) {
			logger.warning(e.toString());
		}
	}

	// 인벤토리 불러오기
	public void loadInventory() {
		try {
			ResultSet rs = DataBase.executeQuery("SELECT * FROM `inventory` WHERE `user_no` = '" + no + "';");
    	
	    	while (rs.next()) {
	    		inventory.put(rs.getInt("index"), 
				    				new GameData.InventoryItem(
				    				rs.getInt("user_no"),
				    				rs.getInt("item_no"),
				    				rs.getInt("amount"),
				    				rs.getInt("index"),
				    				rs.getInt("damage"),
				    				rs.getInt("magic_damage"),
				    				rs.getInt("defense"),
				    				rs.getInt("magic_defense"),
				    				rs.getInt("str"),
				    				rs.getInt("dex"),
				    				rs.getInt("agi"),
				    				rs.getInt("hp"),
				    				rs.getInt("mp"),
				    				rs.getInt("critical"),
				    				rs.getInt("avoid"),
				    				rs.getInt("hit"),
				    				rs.getInt("reinforce"),
				    				rs.getInt("trade")));

	    		ctx.writeAndFlush(Packet.setInventory(inventory.get(rs.getInt("index"))));
	    	}

			rs.close();
		} catch (SQLException e) {
			logger.warning(e.toString());
		}
		
	}
	
	// 아이템 장착
	public void equipItem(int type, int index) {
		switch (type) {
		case GameData.ItemType.WEAPON:
			weapon = index;
			break;
		case GameData.ItemType.SHIELD:
			shield = index;
			break;
		case GameData.ItemType.HELMET:
			helmet = index;
			break;
		case GameData.ItemType.ARMOR:
			armor = index;
			break;
		case GameData.ItemType.CAPE:
			cape = index;
			break;
		case GameData.ItemType.SHOES:
			shoes = index;
			break;
		case GameData.ItemType.ACCESSORY:
			accessory = index;
			break;
		}
	}
	
	public void useStatPoint(int stat) {
		if (statPoint <= 0)
			return;
		
		switch (stat) {
			case GameData.StatusType.STR:
				pureStr++;
				ctx.writeAndFlush(Packet.updateStatus(stat, getStr()));
				break;
			case GameData.StatusType.DEX:
				pureDex++;
				ctx.writeAndFlush(Packet.updateStatus(stat, getDex()));
				break;
			case GameData.StatusType.AGI:
				pureAgi++;
				ctx.writeAndFlush(Packet.updateStatus(stat, getAgi()));
				break;
			default:
				return;
		}

		statPoint--;
		ctx.writeAndFlush(Packet.updateStatus(GameData.StatusType.STAT_POINT, statPoint));
	}

	// NPC로부터 아이템 획득 (번호만으로 아이템 획득)
	public boolean gainItem(int itemNo, int num) {
		int gap = 0;
		int index = getEmptyIndex();
		GameData.Item i = GameData.item.get(itemNo);
		GameData.InventoryItem item = findLazyItemByNo(itemNo);

		// 이미 있던 아이템일 경우 채워줌
		if (item != null) {
			gap = item.getAmount() + num - i.getMaxLoad();
			item.changeAmount(num);
			num = gap;
			ctx.writeAndFlush(Packet.updateInventory(1, item));
		}

		while (num > 0) {
			if (index == -1) {
				// 나머지 아이템 드랍
				return false;
			}
			inventory.put(index, new GameData.InventoryItem(no, itemNo, num, index, i.isTradeable() ? 1 : 0));
			ctx.writeAndFlush(Packet.setInventory(inventory.get(index)));
			index = getEmptyIndex();
			num -= i.getMaxLoad();
		}

		return true;
	}

	public boolean loseItemByNo(int itemNo, int num) {
		if (itemNo <= 0 || num <= 0)
			return false;
		
		int gap = 0;
		GameData.InventoryItem i = findItemByNo(itemNo);

		if (i == null || getTotalItemAmount(i.getItemNo()) < num)
			return false;

		do {
			gap = num - i.getAmount();
			i.changeAmount(-num);
			if (i.getAmount() == 0) {
				inventory.remove(i.getIndex());
				ctx.writeAndFlush(Packet.updateInventory(0, i));
			} else {
				ctx.writeAndFlush(Packet.updateInventory(1, i));
			}
			num = gap;
		} while (num > 0);

		return true;
	}

	public boolean loseItemByIndex(int index, int num) {
		GameData.InventoryItem i = findItemByIndex(index);

		if (i == null || i.getAmount() < num)
			return false;

		i.changeAmount(-num);
		if (i.getAmount() == 0) {
			inventory.remove(i.getIndex());
			ctx.writeAndFlush(Packet.updateInventory(0, i));
		} else {
			ctx.writeAndFlush(Packet.updateInventory(1, i));
		}

		return true;
	}
	
	// 비어있는 인덱스를 획득
	public int getEmptyIndex() {
		for (int i = 0; i < maxInventory; i++) {
			if (!inventory.containsKey(i))
				return i;
		}
		
		return -1;
	}
	
	// 가지고 있는 아이템 총량을 획득
	public int getTotalItemAmount(int no) {
		int num = 0;
		for (GameData.InventoryItem item : inventory.values()) {
			if (item.getItemNo() == no)
				num += item.getAmount();
		}
		
		return num;
	}
	
	// Index로 아이템 검색
	public GameData.InventoryItem findItemByIndex(int index) {
		if (!inventory.containsKey(index))
			return null;
		
		return inventory.get(index);
	}

	// No로 아이템 검색
	public GameData.InventoryItem findItemByNo(int no) {
		for (GameData.InventoryItem item : inventory.values()) {
			if (item.getItemNo() == no)
				return item;
		}

		return null;
	}

	// No로 아이템 검색
	public GameData.InventoryItem findLazyItemByNo(int no) {
		for (GameData.InventoryItem item : inventory.values()) {
			if (item.getItemNo() == no && item.getAmount() < GameData.item.get(item.getItemNo()).getMaxLoad())
				return item;
		}

		return null;
	}

	public void changeItemIndex(int index1, int index2) {
		if (!inventory.containsKey(index1) || !inventory.containsKey(index2))
			return;

		GameData.InventoryItem item1 = inventory.get(index1);
		GameData.InventoryItem item2 = inventory.get(index2);
		inventory.remove(index1);
		inventory.remove(index2);
		item1.setIndex(index2);
		item2.setIndex(index1);
		inventory.put(index2, item1);
		inventory.put(index1, item2);

		ctx.write(Packet.setInventory(item1));
		ctx.writeAndFlush(Packet.setInventory(item2));
	}

	public void turnUp() {
		Map gameMap = Handler.map.get(map);
		direction = GameData.Direction.UP;

		gameMap.sendToOthers(this, Packet.turnCharacter(0, no, direction));
	}

	public void turnDown() {
		Map gameMap = Handler.map.get(map);
		direction = GameData.Direction.DOWN;

		gameMap.sendToOthers(this, Packet.turnCharacter(0, no, direction));
	}

	public void turnLeft() {
		Map gameMap = Handler.map.get(map);
		direction = GameData.Direction.LEFT;

		gameMap.sendToOthers(this, Packet.turnCharacter(0, no, direction));
	}

	public void turnRight() {
		Map gameMap = Handler.map.get(map);
		direction = GameData.Direction.RIGHT;

		gameMap.sendToOthers(this, Packet.turnCharacter(0, no, direction));
	}
	
	public void moveUp() {
		Map gameMap = Handler.map.get(map);
		direction = GameData.Direction.UP;

		if (gameMap.isPassable(x, y - 1)) {
			y -= 1;
			gameMap.sendToOthers(this, Packet.moveCharacter(0, no, x, y, direction));
		} else {
			ctx.writeAndFlush(Packet.userRefresh(this));
		}
	}
	
	public void moveDown() {
		Map gameMap = Handler.map.get(map);
		direction = GameData.Direction.DOWN;

		if (gameMap.isPassable(x, y + 1)) {
			y += 1;
			gameMap.sendToOthers(this, Packet.moveCharacter(0, no, x, y, direction));
		} else {
			ctx.writeAndFlush(Packet.userRefresh(this));
		}
	}
	
	public void moveLeft() {
		Map gameMap = Handler.map.get(map);
		direction = GameData.Direction.LEFT;

		if (gameMap.isPassable(x - 1, y)) {
			x -= 1;
			gameMap.sendToOthers(this, Packet.moveCharacter(0, no, x, y, direction));
		} else {
			ctx.writeAndFlush(Packet.userRefresh(this));
		}
	}
	
	public void moveRight() {
		Map gameMap = Handler.map.get(map);
		direction = GameData.Direction.RIGHT;

		if (gameMap.isPassable(x + 1, y)) {
			x += 1;
			gameMap.sendToOthers(this, Packet.moveCharacter(0, no, x, y, direction));
		} else {
			ctx.writeAndFlush(Packet.userRefresh(this));
		}
	}
	
}
