package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.KnowledgeBookMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper class that contians all Metadata associated with Knowledge Books
 */
public class KnowledgeBookMetadata extends Metadata {

    private static final long serialVersionUID = -8346457494473426580L;

    private List<String> recipes;

    /**
     * Create a new KnowledgeBookMetadata class from scratch with default values.
     */
    public KnowledgeBookMetadata() {
        super();

        this.recipes = new ArrayList<>();
    }

    /**
     * Create a new KnowledgeBookMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item
     * @param section The section from which the data will be parsed
     */
    public KnowledgeBookMetadata(WrappedConfigurationSection section) {
        super(section);

        this.recipes = section.getStringList("recipes");
    }

    /**
     * Create a new KnowledgeBookMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which knowledge book metadata will be extracted
     */
    public KnowledgeBookMetadata(ItemStack item) {
        super(item);

        KnowledgeBookMeta meta = (KnowledgeBookMeta) item.getItemMeta();
        if (meta != null) {
            this.recipes = meta.getRecipes().stream().map(NamespacedKey::toString).collect(Collectors.toList());
        }
    }

    /**
     * Get a list of recipes present in this metadata.
     * @return A mutable list containing all recipes associated with this metadata
     */
    public List<String> getRecipes() {
        return recipes;
    }

    /**
     * Add a new recipe to the list of recipes.
     * @param recipe The recipe to add
     */
    public void addRecipe(String recipe) {
        recipes.add(recipe);
    }

    /**
     * Set all recipes associated with this knowledge book.
     * @param recipes A list of recipes to set
     */
    public void setRecipes(List<String> recipes) {
        this.recipes = recipes;
    }

    /**
     * Apply the knowledge book metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        KnowledgeBookMeta meta = (KnowledgeBookMeta) item.getItemMeta();
        if (recipes != null) {
            meta.setRecipes(recipes.stream().map(NamespacedKey::fromString).collect(Collectors.toList()));
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the knowledge book metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.recipes != null && this.recipes.size() > 0) {
            section.set("recipes", this.recipes);
        }
    }
}
