package com.ralph.discord.chatCommands.character;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

import javax.inject.Named;

import com.ralph.discord.chatCommands.CommandAction;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class CharacterGenerator implements CommandAction {
	
	private static List<String> adjectives = Arrays.asList("Adorable", "Adventurous", "Aggressive", "Agreeable", "Alert", "Alive", "Amused", "Angry", "Annoyed", "Annoying", "Anxious", "Arrogant", "Ashamed", "Attractive", "Average", "Awful", "Bad", "Beautiful", "Better", "Bewildered", "Black", "Bloody", "Blue", "Blue-eyed", "Blushing", "Bored", "Brainy", "Brave", "Breakable", "Bright", "Busy", "Calm", "Careful", "Cautious", "Charming", "Cheerful", "Clean", "Clear", "Clever", "Cloudy", "Clumsy", "Colorful", "Combative", "Comfortable", "Concerned", "Condemned", "Confused", "Cooperative", "Courageous", "Crazy", "Creepy", "Crowded", "Cruel", "Curious", "Cute", "Dangerous", "Dark", "Dead", "Defeated", "Defiant", "Delightful", "Depressed", "Determined", "Different", "Difficult", "Disgusted", "Distinct", "Disturbed", "Dizzy", "Doubtful", "Drab", "Dull", "Eager", "Easy", "Elated", "Elegant", "Embarrassed", "Enchanting", "Encouraging", "Energetic", "Enthusiastic", "Envious", "Evil", "Excited", "Expensive", "Exuberant", "Fair", "Faithful", "Famous", "Fancy", "Fantastic", "Fierce", "Filthy", "Fine", "Foolish", "Fragile", "Frail", "Frantic", "Friendly", "Frightened", "Funny", "Gentle", "Gifted", "Glamorous", "Gleaming", "Glorious", "Good", "Gorgeous", "Graceful", "Grieving", "Grotesque", "Grumpy", "Handsome", "Happy", "Healthy", "Helpful", "Helpless", "Hilarious", "Homeless", "Homely", "Horrible", "Hungry", "Hurt", "Ill", "Important", "Impossible", "Inexpensive", "Innocent", "Inquisitive", "Itchy", "Jealous", "Jittery", "Jolly", "Joyous", "Kind", "Lazy", "Light", "Lively", "Lonely", "Long", "Lovely", "Lucky", "Magnificent", "Misty", "Modern", "Motionless", "Muddy", "Mushy", "Mysterious", "Nasty", "Naughty", "Nervous", "Nice", "Nutty", "Obedient", "Obnoxious", "Odd", "Old-fashioned", "Open", "Outrageous", "Outstanding", "Panicky", "Perfect", "Plain", "Pleasant", "Poised", "Poor", "Powerful", "Precious", "Prickly", "Proud", "Putrid", "Puzzled", "Quaint", "Real", "Relieved", "Repulsive", "Rich", "Scary", "Selfish", "Shiny", "Shy", "Silly", "Sleepy", "Smiling", "Smoggy", "Sore", "Sparkling", "Splendid", "Spotless", "Stormy", "Strange", "Stupid", "Successful", "Super", "Talented", "Tame", "Tasty", "Tender", "Tense", "Terrible", "Thankful", "Thoughtful", "Thoughtless", "Tired", "Tough", "Troubled", "Ugliest", "Ugly", "Uninterested", "Unsightly", "Unusual", "Upset", "Uptight", "Vast", "Victorious", "Vivacious", "Wandering", "Weary", "Wicked", "Wide-eyed", "Wild", "Witty", "Worried", "Worrisome", "Wrong", "Zany", "Zealous");
	private static List<String> nouns = Arrays.asList("Abbot/Abbess", "Abjurer", "Accoucheur/Obstetrician", "Accountant", "Acolyte", "Acrobat", "Actor", "Actuary", "Adventurer", "Affeeror", "Agister", "Alabasterer", "Alchemist", "Alderman", "Ale/Wine Draper", "Ale/Wine Tunner", "Alienist", "Almoner", "Animal Handler", "Anthropologist", "Apothecary", "Appraiser", "Apprentice", "Arborist", "Archaeologist", "Archbishop", "Archduke/Archduchess", "Archer", "Architect", "Archivist", "Archmage", "Aristocrat", "Armorer", "Artificer", "Assassin", "Assay Master", "Assayer", "Astrologer", "Athlete", "Auctioneer", "Augurer", "Bailiff", "Baker", "Baler", "Bandit", "Banker", "Barber", "Barkeep", "Barmaid", "Baron/Baroness", "Bathhouse Owner", "Beekeeper", "Beggar/Pauper", "Billboardposter", "Birdcatcher", "Bishop", "Blacksmith", "Bladesmith", "Blood Hunter/Monster Hunter", "Bloodletter", "Bodyguard", "Bookbinder", "Bookkeeper", "Bosun", "Botanist", "Bottler", "Bouncer", "Bounty Hunter", "Bowyer", "Brewer", "Brickmaker", "Brickmason", "Broom Maker", "Brothel Owner/Pimp", "Burglar", "Busker/Street", "Butcher", "Cabbie/Wagoner", "Candlemaker", "Captain", "Caravan Guard", "Caravaneer", "Cardinal", "Cardmaker", "Caregiver", "Carpenter", "Carter", "Cartographer", "Cartwright", "Castellan", "Cavalier", "Celebrity", "Chancellor", "Chandler", "Chaplain", "Charcoal Maker", "Charioteer", "Charlatan/Conman", "Chatelaine/Majordomo", "Chef", "Chemist", "Chief", "Chimney Sweeper", "Chirurgeon", "Circus Performer", "City Watch", "Claymason", "Clergy", "Cleric", "Clerk", "Cobbler", "Cockfighter/Gamefighter", "Collector", "Commissar", "Conjuror", "Constable", "Construction Worker", "Cook", "Cooper/Hooper", "Copyist", "Count/Countess", "Courier", "Courtier", "Cowherd", "Crime Boss", "Crossbowman", "Croupier", "Cult Leader", "Cultist", "Cutler", "Cutpurse", "Dairyboy/Dairymaid", "Dancer", "Dean", "Deserter", "Detective/Investigator", "Diplomat", "Disgraced Noble", "Dissident", "Distiller", "Diviner", "Doctor", "Drug Dealer", "Drug Lord", "Druid", "Drummer/Fifer", "Duelist", "Duke/Duchess", "Dungeon Delver", "Dyer", "Elder/Retiree", "Elementalist", "Embroiderer", "Emperor/Empress", "Enchanter/Enchantress", "Engineer", "Engraver", "Entrepreneur", "Evoker", "Ex-Criminal", "Executioner", "Exile", "Exorcist", "Explorer", "Falconer", "Far Traveler", "Farmer", "Fashion Designer", "Fence", "Ferryman", "Field Medic/Combat Medic", "Fireman", "First Mate", "Fisher", "Fishmonger", "Fletcher", "Florist", "Folk Hero", "Food & Drink", "Fool", "Forager", "Friar", "Furniture Artisan", "Furrier", "Gambler", "Gamekeeper", "Gardener", "General", "General Contractor", "Gladiator", "Glasspainter", "Glazier/Glassmaker", "Glovemaker", "Goldsmith/Silversmith", "Gongfarmer", "Grave Robber", "Gravedigger", "Grocer", "Groom", "Guard", "Guild Master", "Guilder", "Hatter/Milliner", "Healer", "Hearth-witch", "Heckler", "Helmsman", "Herald", "Herbalist", "Herder", "Heretic", "Hermit", "High Priest/Pope", "Highwayman", "Historian", "Horologist", "Horse Trainer", "Housemaid", "Housewife/Househusband", "Hunter", "Illuminator", "Illusionist", "Innkeeper", "Inquisitor", "Inspection Officer", "Intelligence Officer", "Interpreter", "Ironmonger", "Jailer", "Jem Cutter", "Jester", "Jeweler", "Judge", "Kidnapper", "King/Queen", "Kitchen Drudge", "Knight", "Laborer", "Lady-in-Waiting", "Lamplighter", "Land Surveyor", "Landscaper", "Laundry Worker", "Lawyer/Advocate", "Leatherworker", "Librarian", "Linguist", "Loan SharkAbecedarian", "Locksmith", "Longshoreman", "Luthier", "Mage", "Maid/Butler", "Maker", "Man-at-Arms", "Marquess", "Marshall", "Master-of-Coin", "Master-of-Horses", "Master-of-Hounds", "Mathematician", "Medium", "Mercenary", "Mercer", "Merchant", "MessengerAccoutrement", "Midwife", "Miller", "Miner", "Minister", "Minstrel", "Missionary", "MonkBoatman", "Mortician", "Musician", "Navigator", "Necromancer", "Noble", "Nun", "Nurse", "Orator/Spokesman", "Orphanage Caretaker", "Outlaw", "Page", "Painter", "Paladin", "Pardoner", "Pastry Chef", "Pathfinder", "Peddler", "Philosopher", "Physician", "Pilgrim", "Piper", "Pirate", "Plantation Owner", "Plasterer", "Playwright", "Plumer", "Poacher", "Poet", "Porter", "Potter", "Priest", "Prince/Princess", "Printer", "Privateer", "Professor", "Prophet", "Prospector", "Prostitute", "Purser", "Quarryman", "Quartermaster", "Rag-and-Bone Man", "Raider", "Ranger", "Rebel", "Refugee", "Renderer", "Revels", "Ritualist", "Roadlayer", "Roofer", "Rope-maker", "Royal Guard", "Runaway Slave", "Runecaster", "Runner", "Saddler", "Sage", "Sailor", "Sapper", "Scholar/Researcher", "Scout", "Scribe", "Sculptor", "Sea Captain", "Seamstress/Tailor", "Seer/Oracle", "Senator", "Sentinel", "Sergeant", "Sergeant-at-Arms", "Sexton", "Shaman", "Shapeshifter", "Shepherd", "Shipwright", "Siege Artillerist", "Singer/Soprano", "Skald", "Slave", "Slave Driver", "Smuggler", "Soaper", "Soldier", "Sorcerer/Sorceress", "Spearman", "Special/Secret", "Speculator", "Spy", "Spymaster", "Squatter", "Squire", "Stablehand", "Steward", "Stonemason", "Street Sweeper", "Street Vendor", "Streetlayer", "Student", "Summoner", "Surgeon/Chirurgeon", "Surgeon/Nurse", "Swab", "Tactician", "Tanner", "Taster", "Tattooist", "Tavern Worker", "Tax Collector", "Taxidermist", "Teacher", "Templar", "Thatcher", "Theologian", "Thief/Rogue", "Thresher", "Thriftdealer", "Tinker", "Tollkeeper", "Torturer", "Town Crier", "Town Gossip", "Toymaker", "Tradesman", "Trainer", "Translator", "Transmuter", "Trapper", "Troops", "Tutor", "Urchin", "Vagabond", "Vermin Catcher", "Veterinarian", "VeterinarianAcater", "Vintner", "Viscount/Minister", "Viscount/Viscountess", "Viscountess", "Ward", "Warden", "Warlock", "Warmage", "Watchmaker", "Water Bearer", "Weaponsmith", "Weaver", "Wheelwright", "Whittler", "Witch", "Witchdoctor", "Wizard", "Woodcarver", "Woodcutter", "Wordsmith", "WordsmithAdmiral", "Wrestler/Brawler", "Writer", "Zookeeper");
	private static List<String> vowels = Arrays.asList("A", "E", "I", "O", "U");
	
	@Override
	public void executeAction(MessageReceivedEvent messageReceivedEvent) {		
		int num = extractNumber(messageReceivedEvent);
		
		StringJoiner joiner = new StringJoiner("\n", "Generated characters:\n", "");
		
		for(int i = 0; i < num; i++) {
			joiner.add(generate());
		}
		
		messageReceivedEvent.getTextChannel().sendMessage(joiner.toString()).complete();		
	}
	
	private int extractNumber(MessageReceivedEvent event) {		
		try {
			int num = Integer.parseInt(event.getMessage().getContentRaw().toLowerCase().replace("!generate ", ""));
			return num > 10 ? 10 : num;
		}
		catch (NumberFormatException e) {
			return 1;
		}
	}
	
	
	private String generate() {
		Random random = new Random();
		int aIndex = random.nextInt(adjectives.size());
		int nIndex = random.nextInt(nouns.size());
		
		String adj = adjectives.get(aIndex);
		String noun = nouns.get(nIndex);
		String prefix = vowels.stream().anyMatch(vowel -> adj.startsWith(vowel)) ? "An" : "A";
		
		StringJoiner joiner = new StringJoiner(" ");
		joiner.add(prefix);
		joiner.add(adj);
		joiner.add(noun);
		return joiner.toString();
	}
}
