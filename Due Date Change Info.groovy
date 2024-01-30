// Import libraries
import com.atlassian.jira.component.ComponentAccessor
import java.text.SimpleDateFormat
import com.atlassian.jira.config.LocaleManager

// Disable cache
enableCache = {-> false}

// Initialize simple date format
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

// Initialize locale manager for translations
def localeManager = ComponentAccessor.getLocaleManager()

// Initialize change history manager
def changeHistoryManager = ComponentAccessor.getChangeHistoryManager()

// Get change due date values
def changedDue = changeHistoryManager.getChangeItemsForField(issue, "duedate")

// Get current logged in user's locale
def locale = localeManager.getLocaleFor(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser())

// Check if the changed due is more than 0 or not empty
if (changedDue.size() > 0)
    {
        // The times the first due date have been changed. If the prior due date is null, set it to 0. Otherwise, set to the actual times it has been changed
        def times = changedDue.first().from == null ? changedDue.size()-1 : changedDue.size() 
        
        // Reverse all changed due then get the first prior due date
        def from = changedDue.reverse().first().from
        
        // Reverse all changed due then get the first target due date
        def to = changedDue.reverse().first().to
        
        // Reverse all changed due then get the date the first due date was changed
        def created = changedDue.reverse().first().created
        
        // Reverse all changed due and get the first due date
        def first = changedDue.reverse().first()
        
        // Reverse all changed due
        def reverse = changedDue.reverse()
        
        // If the prior due date and reversed prior due date is null
        if (from == null && reverse.from[1] == null)
            {
                // Return null
				return null
            }
        // If the target due date is null
        else if (to == null)
            {
				return (locale.toString() != "ko_KR") ? "The Due Date has been changed to null from " + from + " on " + sdf.format(created) + ". The Due Date has been changed " + times + " times." : "기한이 " + sdf.format(created) + "에 "+ from + "에서 NULL로 변경되었습니다. " + times + "번 변경되었습니다."
            }
        // If the prior due date is null
        else if (from == null)
            {
				return  (locale.toString() != "ko_KR") ? "The Due Date has been changed to " + to + " from null on " + sdf.format(created) + ". The Due Date has been changed " + times + " times." : "기한이 " + sdf.format(created) + "에 NULL에서 " + to + "로 변경되었습니다. " + times + "번 변경되었습니다."
            }
        // Otherwise
        else
            {
				return  (locale.toString() != "ko_KR") ? "The Due Date has been changed to " + to + " from "+ from + " on " + sdf.format(created) + ". The Due Date has been changed " + times + " times." : "기한이 " + sdf.format(created) + "에 " + from + "에서 "+ to + "로 변경되었습니다. " + times + "번 변경되었습니다."
            }
	}
else
    {
        return null
    }