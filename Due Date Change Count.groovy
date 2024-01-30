// Initialize library
import com.atlassian.jira.component.ComponentAccessor

// Disable cache
enableCache = {-> false}

// Initialize change history manager
def changeHistoryManager = ComponentAccessor.getChangeHistoryManager()

// Get changed due dates
def changedDue = changeHistoryManager.getChangeItemsForField(issue, "duedate")

// Check if chagned due date size is more than 0
if (changedDue.size() > 0)
    {
        // If the prior changed due date is null, put 0. Otherwise, put the change count
        return changedDue.first().from == null ? changedDue.size()-1 : changedDue.size() 
	}
else
    {
        return null
    }