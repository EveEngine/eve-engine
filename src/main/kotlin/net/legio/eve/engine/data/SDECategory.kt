package net.legio.eve.engine.data

import net.legio.eve.engine.data.entity.*

import java.net.URL

enum class SDECategory private constructor(
    val categoryName: String,
    private val urlStr: String,
    val dataClass: Class<*>
) {
    AgentTypes("Agent Types", "http://sde.zzeve.com/agtAgentTypes.json", AgentType::class.java),
    Agents("Agents", "http://sde.zzeve.com/agtAgents.json", Agent::class.java),
    AgentResearchTypes(
        "Agent Research Types",
        "http://sde.zzeve.com/agtResearchAgents.json",
        AgentResearchType::class.java
    ),
    Ancestries("Ancestries", "http://sde.zzeve.com/chrAncestries.json", Ancestry::class.java),
    Attributes("Attributes", "http://sde.zzeve.com/chrAttributes.json", Attribute::class.java),
    Bloodlines("Bloodlines", "http://sde.zzeve.com/chrBloodlines.json", Bloodline::class.java),
    Categories("Inventory Categories", "http://sde.zzeve.com/invCategories.json", Category::class.java),
    Certs("Certificates", "http://sde.zzeve.com/certCerts.json", Cert::class.java),
    Factions("Factions", "http://sde.zzeve.com/chrFactions.json", Faction::class.java),
    Groups("Inventory Groups", "http://sde.zzeve.com/invGroups.json", InventoryGroup::class.java),
    Items("Inventory Items", "http://sde.zzeve.com/invItems.json", Item::class.java),
    Masteries("Masteries", "http://sde.zzeve.com/certMasteries.json", Mastery::class.java),
    Names("Inventory Names", "http://sde.zzeve.com/invNames.json", InventoryName::class.java),
    NPCCorporationDivisions(
        "NPC Corporation Divisions",
        "http://sde.zzeve.com/crpNPCCorporationDivisions.json",
        NPCCorpDivision::class.java
    ),
    NPCCorporationResearchFields(
        "NPC Corporation Research Fields",
        "http://sde.zzeve.com/crpNPCCorporationResearchFields.json",
        CorpResearchField::class.java
    ),
    NPCCorporationTrades(
        "NPC Corporation Trades",
        "http://sde.zzeve.com/crpNPCCorporationTrades.json",
        CorpTrade::class.java
    ),
    NPCCorporations("NPC Corporations", "http://sde.zzeve.com/crpNPCCorporations.json", NPCCorporation::class.java),
    NPCDivisions("NPC Divisions", "http://sde.zzeve.com/crpNPCDivisions.json", CorpDivision::class.java),
    Races("Races", "http://sde.zzeve.com/chrRaces.json", Race::class.java),
    Skills("Skills", "http://sde.zzeve.com/certSkills.json", Skill::class.java),
    TypeMaterials("Inventory Type Materials", "http://sde.zzeve.com/invTypeMaterials.json", TypeMaterial::class.java),
    Types("Inventory Types", "http://sde.zzeve.com/invTypes.json", Type::class.java),
    Volumes("Inventory Volumes", "http://sde.zzeve.com/invVolumes.json", Volume::class.java);

    val categoryURL: URL = URL(urlStr)

    val fileName: String
        get() = this.urlStr.substring(this.urlStr.lastIndexOf("/") + 1)

}
