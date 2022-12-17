# Event Listeners
### This is the tutorial page for using events in your miscript

## Getting started
### Why?
You will often find yourself in a situation, where your script should do something when some precise event happens on the server. This includes stuff
like blocks being placed or broken, players doing anything, etc. To add code and make it run when any event happens, read this tutorial.
### Registering an Event Listener
To get started, head into your `script.toml` file and add this line for every event you want to listen to:
```toml
[[listeners]]
event = "YourEventNameHere"
```
At the end of this file you will find a list of all possible events.
### Custom priority for Event Listener
You may want to have your event listener run before another scripts event listener for the same event. To accomplish that, you use a custom priority.
```toml
[[listeners]]
event = "YourEventNameHere"
priority = 1000
```
The default value is 1000 (when not set), but can be lowered or highered to be prioritised over other event listeners
(or not, if you want yours to run as the last).
### Adding code for your Event Listener
To add code that runs when a specific event happens (after having done the above), head into your `script.mi` script file.
In your script module (the module specified in your `script.toml` file), add a function in the following scheme:
```
fn XYZEvent(type1 name1, type2 name2, ...) {

}
```
An event can, in some cases, accept event-specific arguments, for example you will definetly need the player that did something when listening
for player events. As an example, let's take the PlayerJoinEvent.
```
fn PlayerJoinEvent(string player_uuid) {

}
```
This function will be run when a player joins, and it accepts the player uuid as an argument. 
To, for example, send the player a message when they join, you could do the following:
```
fn PlayerJoinEvent(string player_uuid) {
    use misc;
    message(player_uuid, "hello!", true /*are we using uuids or names? uuids, so this is true*/);
}
```
The same can be applied for any other event. Again, below you will find a list of all events, including their arguments. Most events currently don't have
any arguments, but those will be added when needed.
#### Note: You have to add all arguments, even if you don't want to use them all. This is a temporary solution, as the event cannot be abstracted into its own class / struct (mi simply doesn't have those yet)
### Cancelling Events
Some events can also be cancelled and prevented from happening. To cancel events in miscript, you simply make your event listener function
return a boolean value.
```
fn FoodLevelChangeEvent :: bool (string player_uuid, int new_food_level, int previous_food_level) {
    return true; // do we want to cancel it? YES; so the return value here must be true.
}
```
To see which events can be cancelled, see the below list. Trying to cancel an event that cannot be cancelled will simply not do anything.
# The Event List (use the spigotmc docs to check for what the event does)
```
cancellable AnvilDamagedEvent                           
cancellable BeaconEffectEvent                           
cancellable BlockDestroyEvent                           
cancellable TNTPrimeEvent                           
not cancellable AsyncPlayerSendCommandsEvent                           
cancellable AsyncPlayerSendSuggestionsEvent                           
cancellable CommandRegisteredEvent                           
cancellable CreeperIgniteEvent                           
cancellable EnderDragonFireballHitEvent                           
cancellable EnderDragonFlameEvent                           
cancellable EnderDragonShootFireballEvent                           
cancellable EndermanAttackPlayerEvent                           
cancellable EndermanEscapeEvent                           
not cancellable EntityAddToWorldEvent                           
cancellable EntityJumpEvent                           
cancellable EntityKnockbackByEntityEvent                           
cancellable EntityPathfindEvent                           
not cancellable EntityRemoveFromWorldEvent                           
cancellable EntityTeleportEndGatewayEvent                           
cancellable EntityTransformedEvent                           
cancellable EntityZapEvent                           
cancellable ExperienceOrbMergeEvent                           
cancellable PhantomPreSpawnEvent                           
cancellable PlayerNaturallySpawnCreaturesEvent                           
cancellable PreCreatureSpawnEvent                           
cancellable PreSpawnerSpawnEvent                           
cancellable ProjectileCollideEvent                           
cancellable SkeletonHorseTrapEvent                           
cancellable SlimeChangeDirectionEvent                           
cancellable SlimePathfindEvent                           
cancellable SlimeSwimEvent                           
cancellable SlimeTargetLivingEntityEvent                           
cancellable SlimeWanderEvent                           
not cancellable ThrownEggHatchEvent                           
cancellable TurtleGoHomeEvent                           
cancellable TurtleLayEggEvent                           
cancellable TurtleStartDiggingEvent                           
cancellable WitchConsumePotionEvent                           
cancellable WitchReadyPotionEvent                           
cancellable WitchThrowPotionEvent                           
not cancellable PrepareGrindstoneEvent                           
not cancellable PrepareResultEvent                           
not cancellable IllegalPacketEvent                           
cancellable PlayerAdvancementCriterionGrantEvent                           
not cancellable PlayerArmorChangeEvent                           
cancellable PlayerAttackEntityCooldownResetEvent                           
not cancellable PlayerClientOptionsChangeEvent                           
not cancellable PlayerConnectionCloseEvent                           
cancellable PlayerElytraBoostEvent                           
cancellable PlayerHandshakeEvent                           
not cancellable PlayerInitialSpawnEvent                           
cancellable PlayerJumpEvent                           
cancellable PlayerLaunchProjectileEvent                           
not cancellable PlayerLocaleChangeEvent                           
cancellable PlayerPickupExperienceEvent                           
not cancellable PlayerPostRespawnEvent                           
cancellable PlayerReadyArrowEvent                           
cancellable PlayerRecipeBookClickEvent                           
cancellable PlayerSetSpawnEvent                           
cancellable PlayerStartSpectatingEntityEvent                           
cancellable PlayerStopSpectatingEntityEvent                           
cancellable PlayerTeleportEndGatewayEvent                           
not cancellable PlayerUseUnknownEntityEvent                           
not cancellable FillProfileEvent                           
not cancellable LookupProfileEvent                           
not cancellable PreFillProfileEvent                           
not cancellable PreLookupProfileEvent                           
not cancellable ProfileWhitelistVerifyEvent                           
cancellable AsyncTabCompleteEvent                           
not cancellable GS4QueryEvent                           
cancellable PaperServerListPingEvent                           
not cancellable ServerExceptionEvent                           
not cancellable ServerTickEndEvent                           
not cancellable ServerTickStartEvent                           
not cancellable WhitelistToggleEvent                           
cancellable LootableInventoryReplenishEvent                           
not cancellable BeaconActivatedEvent                           
not cancellable BeaconDeactivatedEvent                           
cancellable BellRevealRaiderEvent                           
cancellable BellRingEvent                           
not cancellable BlockBreakBlockEvent                           
not cancellable BlockFailedDispenseEvent                           
cancellable BlockPreDispenseEvent                           
cancellable DragonEggFormEvent                           
cancellable PlayerShearBlockEvent                           
cancellable TargetHitEvent                           
cancellable ElderGuardianAppearanceEvent                           
cancellable EntityDamageItemEvent                           
cancellable EntityDyeEvent                           
cancellable EntityInsideBlockEvent                           
cancellable EntityLoadCrossbowEvent                           
cancellable EntityMoveEvent                           
cancellable EntityPortalReadyEvent                           
cancellable EntityToggleSitEvent                           
cancellable PufferFishStateChangeEvent                           
cancellable TameableDeathMessageEvent                           
cancellable WardenAngerChangeEvent                           
not cancellable PlayerChunkLoadEvent                           
not cancellable PlayerChunkUnloadEvent                           
cancellable AsyncChatCommandDecorateEvent                           
cancellable AsyncChatDecorateEvent                           
cancellable AsyncChatEvent                           
cancellable ChatEvent                           
cancellable PlayerArmSwingEvent                           
cancellable PlayerBedFailEnterEvent                           
cancellable PlayerChangeBeaconEffectEvent                           
cancellable PlayerDeepSleepEvent                           
cancellable PlayerFlowerPotManipulateEvent                           
not cancellable PlayerInventorySlotChangeEvent                           
cancellable PlayerItemCooldownEvent                           
cancellable PlayerItemFrameChangeEvent                           
cancellable PlayerLecternPageChangeEvent                           
cancellable PlayerLoomPatternSelectEvent                           
cancellable PlayerNameEntityEvent                           
cancellable PlayerPurchaseEvent                           
cancellable PlayerSignCommandPreprocessEvent                           
cancellable PlayerStonecutterRecipeSelectEvent                           
not cancellable PlayerStopUsingItemEvent                           
not cancellable PlayerTrackEntityEvent                           
cancellable PlayerTradeEvent                           
not cancellable PlayerUntrackEntityEvent                           
cancellable PrePlayerAttackEntityEvent                           
not cancellable ServerResourcesReloadedEvent                           
cancellable StructureLocateEvent                           
cancellable StructuresLocateEvent                           
cancellable WorldGameRuleChangeEvent                           
cancellable WorldBorderBoundsChangeEvent                           
not cancellable WorldBorderBoundsChangeFinishEvent                           
cancellable WorldBorderCenterChangeEvent                           
cancellable BlockBreakEvent                           
cancellable BlockBurnEvent                           
not cancellable BlockCanBuildEvent                           
cancellable BlockCookEvent                           
not cancellable BlockDamageAbortEvent                           
cancellable BlockDamageEvent                           
cancellable BlockDispenseArmorEvent                           
cancellable BlockDispenseEvent                           
cancellable BlockDropItemEvent                           
not cancellable BlockExpEvent                           
cancellable BlockExplodeEvent                           
cancellable BlockFadeEvent                           
cancellable BlockFertilizeEvent                           
cancellable BlockFormEvent                           
cancellable BlockFromToEvent                           
cancellable BlockGrowEvent                           
cancellable BlockIgniteEvent                           
cancellable BlockMultiPlaceEvent                           
cancellable BlockPhysicsEvent                           
cancellable BlockPistonExtendEvent                           
cancellable BlockPistonRetractEvent                           
cancellable BlockPlaceEvent                           
cancellable BlockReceiveGameEvent                           
not cancellable BlockRedstoneEvent                           
cancellable BlockShearEntityEvent                           
cancellable BlockSpreadEvent                           
cancellable CauldronLevelChangeEvent                           
cancellable EntityBlockFormEvent                           
cancellable FluidLevelChangeEvent                           
cancellable LeavesDecayEvent                           
cancellable MoistureChangeEvent                           
cancellable NotePlayEvent                           
cancellable SignChangeEvent                           
cancellable SpongeAbsorbEvent                           
not cancellable UnknownCommandEvent                           
cancellable EnchantItemEvent                           
cancellable PrepareItemEnchantEvent                           
cancellable AreaEffectCloudApplyEvent                           
cancellable ArrowBodyCountChangeEvent                           
cancellable BatToggleSleepEvent                           
cancellable CreatureSpawnEvent                           
cancellable CreeperPowerEvent                           
cancellable EnderDragonChangePhaseEvent                           
cancellable EntityAirChangeEvent(string entity_uuid, string entity_type)
cancellable EntityBreakDoorEvent                           
cancellable EntityBreedEvent                           
cancellable EntityChangeBlockEvent                           
cancellable EntityCombustByBlockEvent                           
cancellable EntityCombustByEntityEvent                           
cancellable EntityCombustEvent                           
cancellable EntityCreatePortalEvent                           
cancellable EntityDamageByBlockEvent                           
cancellable EntityDamageByEntityEvent                           
cancellable EntityDamageEvent                           
cancellable EntityDeathEvent                           
cancellable EntityDropItemEvent                           
cancellable EntityEnterBlockEvent                           
cancellable EntityEnterLoveModeEvent                           
cancellable EntityExhaustionEvent                           
cancellable EntityExplodeEvent                           
cancellable EntityInteractEvent                           
cancellable EntityPickupItemEvent                           
cancellable EntityPlaceEvent                           
not cancellable EntityPortalEnterEvent                           
cancellable EntityPortalEvent                           
cancellable EntityPortalExitEvent                           
not cancellable EntityPoseChangeEvent                           
cancellable EntityPotionEffectEvent                           
cancellable EntityRegainHealthEvent                           
cancellable EntityResurrectEvent                           
cancellable EntityShootBowEvent                           
cancellable EntitySpawnEvent                           
cancellable EntitySpellCastEvent                           
cancellable EntityTameEvent                           
cancellable EntityTargetEvent                           
cancellable EntityTargetLivingEntityEvent                           
cancellable EntityTeleportEvent                           
cancellable EntityToggleGlideEvent                           
cancellable EntityToggleSwimEvent                           
cancellable EntityTransformEvent                           
cancellable EntityUnleashEvent                           
cancellable ExpBottleEvent                           
cancellable ExplosionPrimeEvent                           
cancellable FireworkExplodeEvent                           
cancellable FoodLevelChangeEvent(string player_uuid, int new_food_level, int previous_food_level)
cancellable HorseJumpEvent                           
cancellable ItemDespawnEvent                           
cancellable ItemMergeEvent                           
cancellable ItemSpawnEvent                           
cancellable LingeringPotionSplashEvent                           
cancellable PigZapEvent                           
cancellable PigZombieAngerEvent                           
cancellable PiglinBarterEvent                           
cancellable PlayerDeathEvent                           
cancellable PlayerLeashEntityEvent                           
cancellable PotionSplashEvent                           
cancellable ProjectileHitEvent                           
cancellable ProjectileLaunchEvent                           
cancellable SheepDyeWoolEvent                           
cancellable SheepRegrowWoolEvent                           
cancellable SlimeSplitEvent                           
cancellable SpawnerSpawnEvent                           
cancellable StriderTemperatureChangeEvent                           
cancellable VillagerAcquireTradeEvent                           
cancellable VillagerCareerChangeEvent                           
cancellable VillagerReplenishTradeEvent                           
cancellable HangingBreakByEntityEvent                           
cancellable HangingBreakEvent                           
cancellable HangingPlaceEvent                           
cancellable BrewEvent                           
cancellable BrewingStandFuelEvent                           
cancellable CraftItemEvent                           
cancellable FurnaceBurnEvent                           
not cancellable FurnaceExtractEvent                           
cancellable FurnaceSmeltEvent                           
not cancellable FurnaceStartSmeltEvent                           
cancellable InventoryClickEvent                           
not cancellable InventoryCloseEvent                           
cancellable InventoryCreativeEvent                           
cancellable InventoryDragEvent                           
not cancellable InventoryEvent                           
cancellable InventoryMoveItemEvent                           
cancellable InventoryOpenEvent                           
cancellable InventoryPickupItemEvent                           
not cancellable PrepareAnvilEvent                           
not cancellable PrepareItemCraftEvent                           
not cancellable PrepareSmithingEvent                           
cancellable SmithItemEvent                           
cancellable TradeSelectEvent                           
cancellable AsyncPlayerChatEvent                           
cancellable AsyncPlayerChatPreviewEvent                           
not cancellable AsyncPlayerPreLoginEvent                           
not cancellable PlayerAdvancementDoneEvent                           
cancellable PlayerAnimationEvent                           
cancellable PlayerArmorStandManipulateEvent                           
cancellable PlayerAttemptPickupItemEvent                           
cancellable PlayerBedEnterEvent                           
cancellable PlayerBedLeaveEvent                           
cancellable PlayerBucketEmptyEvent                           
cancellable PlayerBucketEntityEvent                           
cancellable PlayerBucketFillEvent                           
cancellable PlayerBucketFishEvent                           
not cancellable PlayerChangedMainHandEvent                           
not cancellable PlayerChangedWorldEvent                           
cancellable PlayerChatEvent                           
not cancellable PlayerChatTabCompleteEvent                           
cancellable PlayerCommandPreprocessEvent(string player_uuid, string message)
not cancellable PlayerCommandSendEvent                           
cancellable PlayerDropItemEvent                           
cancellable PlayerEditBookEvent                           
not cancellable PlayerEggThrowEvent                           
not cancellable PlayerExpChangeEvent                           
cancellable PlayerFishEvent                           
cancellable PlayerGameModeChangeEvent                           
cancellable PlayerHarvestBlockEvent                           
not cancellable PlayerHideEntityEvent                           
cancellable PlayerInteractAtEntityEvent                           
cancellable PlayerInteractEntityEvent                           
cancellable PlayerInteractEvent                           
not cancellable PlayerItemBreakEvent                           
cancellable PlayerItemConsumeEvent                           
cancellable PlayerItemDamageEvent                           
cancellable PlayerItemHeldEvent                           
cancellable PlayerItemMendEvent                           
not cancellable PlayerJoinEvent(string player_uuid)
cancellable PlayerKickEvent                           
not cancellable PlayerLevelChangeEvent                           
not cancellable PlayerLocaleChangeEvent                           
not cancellable PlayerLoginEvent                           
cancellable PlayerMoveEvent                           
cancellable PlayerPickupArrowEvent                           
cancellable PlayerPickupItemEvent                           
cancellable PlayerPortalEvent                           
not cancellable PlayerPreLoginEvent                           
not cancellable PlayerQuitEvent                           
cancellable PlayerRecipeDiscoverEvent                           
not cancellable PlayerRegisterChannelEvent                           
not cancellable PlayerResourcePackStatusEvent                           
not cancellable PlayerRespawnEvent(string player_uuid)
not cancellable PlayerRiptideEvent                           
cancellable PlayerShearEntityEvent                           
not cancellable PlayerShowEntityEvent                           
cancellable PlayerStatisticIncrementEvent                           
cancellable PlayerSwapHandItemsEvent                           
cancellable PlayerTakeLecternBookEvent                           
cancellable PlayerTeleportEvent                           
cancellable PlayerToggleFlightEvent                           
cancellable PlayerToggleSneakEvent                           
cancellable PlayerToggleSprintEvent                           
cancellable PlayerUnleashEntityEvent                           
not cancellable PlayerUnregisterChannelEvent                           
cancellable PlayerVelocityEvent                           
not cancellable RaidFinishEvent                           
not cancellable RaidSpawnWaveEvent                           
not cancellable RaidStopEvent                           
cancellable RaidTriggerEvent                           
cancellable BroadcastMessageEvent                           
not cancellable MapInitializeEvent                           
not cancellable PluginDisableEvent                           
not cancellable PluginEnableEvent                           
cancellable RemoteServerCommandEvent                           
cancellable ServerCommandEvent(string command)
not cancellable ServerListPingEvent                           
not cancellable ServerLoadEvent                           
not cancellable ServiceRegisterEvent                           
not cancellable ServiceUnregisterEvent                           
cancellable TabCompleteEvent                           
not cancellable VehicleBlockCollisionEvent                           
cancellable VehicleCreateEvent                           
cancellable VehicleDamageEvent                           
cancellable VehicleDestroyEvent                           
cancellable VehicleEnterEvent                           
cancellable VehicleEntityCollisionEvent                           
cancellable VehicleExitEvent                           
not cancellable VehicleMoveEvent                           
not cancellable VehicleUpdateEvent                           
cancellable LightningStrikeEvent                           
cancellable ThunderChangeEvent                           
cancellable WeatherChangeEvent                           
not cancellable ChunkLoadEvent                           
not cancellable ChunkPopulateEvent                           
not cancellable ChunkUnloadEvent                           
not cancellable EntitiesLoadEvent                           
not cancellable EntitiesUnloadEvent                           
cancellable GenericGameEvent                           
cancellable LootGenerateEvent                           
cancellable PortalCreateEvent                           
not cancellable SpawnChangeEvent                           
cancellable StructureGrowEvent                           
cancellable TimeSkipEvent                           
not cancellable WorldInitEvent                           
not cancellable WorldLoadEvent                           
not cancellable WorldSaveEvent                           
cancellable WorldUnloadEvent                           
cancellable EntityDismountEvent                           
cancellable EntityMountEvent                           
not cancellable PlayerSpawnLocationEvent
```