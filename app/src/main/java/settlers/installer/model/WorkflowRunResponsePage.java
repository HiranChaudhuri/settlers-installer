/*
 */
package settlers.installer.model;

import java.util.List;

/**
 *
 * @author hiran
 */
public class WorkflowRunResponsePage {
    private int total_count;
    private List<WorkflowRun> workflow_runs;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<WorkflowRun> getWorkflow_runs() {
        return workflow_runs;
    }

    public void setWorkflow_runs(List<WorkflowRun> workflow_runs) {
        this.workflow_runs = workflow_runs;
    }
    
    
}
