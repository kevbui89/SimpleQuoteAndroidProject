package cs.dawson.myapplication.beans;

/**
 * This is this the Quote object.  It will hold all the data from the database.
 */

public class Quote {
    // Variables
    private String category;
    private String attributed;
    private String dateOfBirth;
    private String blurb;
    private String quoteShort;
    private String quoteFull;
    private String reference;
    private String date;

    /**
     * Default constructor
     */
    public Quote() {
        this("", "", "", "", "", "", "", "");
    }

    /**
     * 7 Param constructor
     * @param attributed    The attributed person
     * @param dateOfBirth   The date of birth of the person
     * @param blurb         Description of the attributed
     * @param qShort        The short quote
     * @param qLong         The long quote
     * @param ref           The reference to the quote
     * @param date          The date added
     */
    public Quote(String category, String attributed, String blurb, String date,
                 String dateOfBirth, String qLong, String qShort, String ref) {
        this.category = category;
        this.attributed = attributed;
        this.blurb = blurb;
        this.date = date;
        this.dateOfBirth = dateOfBirth;
        this.quoteFull = qLong;
        this.quoteShort = qShort;
        this.reference = ref;
    }

    /**
     * Returns the category
     * @return  The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category
     * @param c The category
     */
    public void setCategory(String c) {
        this.category = c;
    }

    /**
     * Returns the attributed
     * @return  The attributed person
     */
    public String getAttributed() {
        return attributed;
    }

    /**
     * Sets the attributed person
     * @param p The attributed person to be set
     */
    public void setAttributed(String p) {
        this.attributed = p;
    }

    /**
     * Returns the date of birth
     * @return  The date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth
     * @param dob   The date of birth to be set
     */
    public void setDateOfBirth(String dob) {
        this.dateOfBirth = dob;
    }

    /**
     * Returns the blurb
     * @return  The description of the attributed
     */
    public String getBlurb() {
        return blurb;
    }

    /**
     * Sets the blurb
     * @param b The blurb to be set
     */
    public void setBlurb(String b) {
        this.blurb = b;
    }

    /**
     * Returns the short quote
     * @return  The short quote
     */
    public String getQuoteShort() {
        return quoteShort;
    }

    /**
     * Sets the short quote
     * @param qs    The short quote to be set
     */
    public void setQuoteShort(String qs) {
        this.quoteShort = qs;
    }

    /**
     * Returns the full quote
     * @return  The full quote
     */
    public String getQuoteFull() {
        return quoteFull;
    }

    /**
     * Sets the full quote
     * @param qf    The full quote to be set
     */
    public void setQuoteFull(String qf) {
        this.quoteFull = qf;
    }

    /**
     * Returns the reference of the quote
     * @return  The reference of the quote
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the reference of the quote
     * @param r The reference to be set
     */
    public void setReference(String r) {
        this.reference = r;
    }

    /**
     * Returns the date added
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date added
     * @param d The date added to be set
     */
    public void setDate(String d) {
        this.date = d;
    }

    /**
     * Checks if both objects are equal
     * @param obj   The object to be compared
     * @return      A boolean determining if the object is equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Quote other = (Quote) obj;
        if (!this.getAttributed().equals(other.getAttributed()))
            return false;
        if (!this.getDateOfBirth().equals(other.getDateOfBirth()))
            return false;
        if (!this.getBlurb().equals(other.getBlurb()))
            return false;
        if (!this.getQuoteShort().equals(other.getQuoteShort()))
            return false;
        if (!this.getQuoteFull().equals(other.getQuoteFull()))
            return false;
        if (!this.getReference().equals(other.getReference()))
            return false;
        if (!this.getDate().equals(other.getDate()))
            return false;

        return true;
    }

    /**
     * Returns a string representation of the object
     * @return  The string of the object
     */
    @Override
    public String toString() {
        return "Quote{person: " + attributed + ", dateofbirth: " +
                dateOfBirth + ", blurb: " + blurb + ", quoteShort: " + quoteShort +
                ", quoteFull: " + quoteFull + ", reference: " + reference + ", date: " +
                date + "}";
    }
}
