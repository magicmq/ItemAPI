package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper class that contians all Metadata associated with Books.
 */
public class BookMetadata extends Metadata implements Serializable {

    private static final long serialVersionUID = 5743882354883646388L;

    private String title;
    private String author;
    private String generation;
    private List<String> pages;

    /**
     * Create a new BookMetadata class from scratch with default values.
     */
    public BookMetadata() {
        super();

        this.title = null;
        this.author = null;
        this.generation = null;
        this.pages = new ArrayList<>();
    }

    /**
     * Create a new BookMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "book-data" section.
     * @param section The section from which the data will be parsed
     */
    public BookMetadata(WrappedConfigurationSection section) {
        super(section);

        WrappedConfigurationSection bookSection = section.getConfigurationSection("book-data");
        title = bookSection.getString("title");
        author = bookSection.getString("author");
        generation = bookSection.getString("generation");
        pages = bookSection.getStringList("pages");
    }

    /**
     * Create a new BookMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which book metadata will be extracted
     */
    public BookMetadata(ItemStack item) {
        super(item);

        BookMeta meta = (BookMeta) item.getItemMeta();
        if (meta != null) {
            this.title = meta.getTitle();
            this.author = meta.getAuthor();
            if (meta.getGeneration() != null)
                this.generation = meta.getGeneration().name();
            this.pages = meta.getPages();
        } else {
            this.title = null;
            this.author = null;
            this.generation = null;
            this.pages = new ArrayList<>();
        }
    }

    /**
     * Get the title of the book.
     * @return The title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the book. Make sure to parse color codes as they are not parsed here.
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the author of the book.
     * @return The author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the author of the book. Make sure to parse color codes as they are not parse here.
     * @param author The author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get the generation of the book.
     * @return The generation of the book
     */
    public String getGeneration() {
        return generation;
    }

    /**
     * Set the generation of the book. There are four possible values: ORIGINAL, COPY_OF_ORIGINAL, COPY_OF_COPY, and TATTERED.
     * @param generation The generation to set
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     * Get a mutable {@link List list} containing the pages of the book, where the index of the element is the page number.
     * @return A mutable list containing all pages in the book. All changes made to the list will persist
     */
    public List<String> getPages() {
        return pages;
    }

    /**
     * Set the pages of the book. Make sure to parse color codes as they are not parsed here.
     * @param pages The pages of the book to set
     */
    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    /**
     * Apply the book metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        BookMeta meta = (BookMeta) item.getItemMeta();
        if (title != null)
            meta.setTitle(ChatColor.translateAlternateColorCodes('&', title));
        if (author != null)
            meta.setAuthor(ChatColor.translateAlternateColorCodes('&', author));
        if (generation != null)
            meta.setGeneration(BookMeta.Generation.valueOf(generation));
        if (pages != null)
            meta.setPages(pages.stream().map(string -> ChatColor.translateAlternateColorCodes('&', string)).collect(Collectors.toList()));

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the book metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        WrappedConfigurationSection bookSection = section.createConfigurationSection("book-data");
        if (this.title != null)
            bookSection.set("title", this.title.replaceAll("\u00A7", "&"));
        if (this.author != null)
            bookSection.set("author", this.author.replaceAll("\u00A7", "&"));
        if (this.generation != null)
            bookSection.set("generation", this.generation);
        if (this.pages != null && this.pages.size() > 0)
            bookSection.set("pages", this.pages.stream().map(string -> string.replaceAll("\u00A7", "&")).collect(Collectors.toList()));
    }
}
