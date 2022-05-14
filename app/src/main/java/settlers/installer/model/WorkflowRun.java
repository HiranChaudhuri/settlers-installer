/*
 */
package settlers.installer.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import settlers.installer.Util;

/**
 * 
 * https://api.github.com/repos/paulwedeck/settlers-remake/actions/runs
 *
 * @author hiran
 */
public class WorkflowRun {
    private static final Logger log = LogManager.getLogger(WorkflowRun.class);
    
    private String id;
    private String name;
    private String node_id;
    private String head_branch;
    private int run_number;
    private String event;
    private String status;
    private String conclusion;
    private String workflow_id;
    private String check_suite_id;
    private String check_suite_node_id;
    private String url;
    private String html_url;
    private Date created_at;
    private Date updated_at;
    private Author actor;
    private int run_attempt;
    private Date run_started_at;
    private Author triggering_actor;
    private String jobs_url;
    private String logs_url;
    private String check_suite_url;
    private String artifacts_url;
    private String cancel_url;
    private String previous_attempt_url;
    private String workflow_url;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getHead_branch() {
        return head_branch;
    }

    public void setHead_branch(String head_branch) {
        this.head_branch = head_branch;
    }

    public int getRun_number() {
        return run_number;
    }

    public void setRun_number(int run_number) {
        this.run_number = run_number;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getWorkflow_id() {
        return workflow_id;
    }

    public void setWorkflow_id(String workflow_id) {
        this.workflow_id = workflow_id;
    }

    public String getCheck_suite_id() {
        return check_suite_id;
    }

    public void setCheck_suite_id(String check_suite_id) {
        this.check_suite_id = check_suite_id;
    }

    public String getCheck_suite_node_id() {
        return check_suite_node_id;
    }

    public void setCheck_suite_node_id(String check_suite_node_id) {
        this.check_suite_node_id = check_suite_node_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Author getActor() {
        return actor;
    }

    public void setActor(Author actor) {
        this.actor = actor;
    }

    public int getRun_attempt() {
        return run_attempt;
    }

    public void setRun_attempt(int run_attempt) {
        this.run_attempt = run_attempt;
    }

    public Date getRun_started_at() {
        return run_started_at;
    }

    public void setRun_started_at(Date run_started_at) {
        this.run_started_at = run_started_at;
    }

    public Author getTriggering_actor() {
        return triggering_actor;
    }

    public void setTriggering_actor(Author triggering_actor) {
        this.triggering_actor = triggering_actor;
    }

    public String getJobs_url() {
        return jobs_url;
    }

    public void setJobs_url(String jobs_url) {
        this.jobs_url = jobs_url;
    }

    public String getLogs_url() {
        return logs_url;
    }

    public void setLogs_url(String logs_url) {
        this.logs_url = logs_url;
    }

    public String getCheck_suite_url() {
        return check_suite_url;
    }

    public void setCheck_suite_url(String check_suite_url) {
        this.check_suite_url = check_suite_url;
    }

    public String getArtifacts_url() {
        return artifacts_url;
    }

    public void setArtifacts_url(String artifacts_url) {
        this.artifacts_url = artifacts_url;
    }

    public String getCancel_url() {
        return cancel_url;
    }

    public void setCancel_url(String cancel_url) {
        this.cancel_url = cancel_url;
    }

    public String getPrevious_attempt_url() {
        return previous_attempt_url;
    }

    public void setPrevious_attempt_url(String previous_attempt_url) {
        this.previous_attempt_url = previous_attempt_url;
    }

    public String getWorkflow_url() {
        return workflow_url;
    }

    public void setWorkflow_url(String workflow_url) {
        this.workflow_url = workflow_url;
    }
    
    public List<Artifact> getArtifacts() {
        try {
        URL u = new URL(artifacts_url);
            InputStream in = u.openStream();

            ArtifactResponsePage arp = Util.getGenson().deserialize(in, ArtifactResponsePage.class);
            List<Artifact> as = (List)(arp.getArtifacts());
            return Util.sortArtifactsByDate(as);
        } catch (IOException e) {
            log.warn("Could not load artifact list {}", artifacts_url, e);
            return null;
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName()).append("(");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
//        sb.append(", node_id=").append(node_id);
        sb.append(", head_branch=").append(head_branch);
        sb.append(", run_number=").append(run_number);
//        sb.append(", event=").append(event);
        sb.append(", status=").append(status);
        sb.append(", conclusion=").append(conclusion);
//        sb.append(", workflow_id=").append(workflow_id);
//        sb.append(", check_suite_id=").append(check_suite_id);
//        sb.append(", check_suite_node_id=").append(check_suite_node_id);
//        sb.append(", url=").append(url);
//        sb.append(", html_url=").append(html_url);
        sb.append(", created_at=").append(created_at);
        sb.append(", updated_at=").append(updated_at);
//        sb.append(", actor=").append(actor);
//        sb.append(", run_attempt=").append(run_attempt);
        sb.append(", run_started_at=").append(run_started_at);
//        sb.append(", triggering_actor=").append(triggering_actor);
//        sb.append(", jobs_url=").append(jobs_url);
//        sb.append(", logs_url=").append(logs_url);
//        sb.append(", check_suite_url=").append(check_suite_url);
//        sb.append(", artifacts_url=").append(artifacts_url);
//        sb.append(", cancel_url=").append(cancel_url);
//        sb.append(", previous_attempt_url=").append(previous_attempt_url);
//        sb.append(", workflow_url=").append(workflow_url);
        sb.append(")");
        return sb.toString();
    }
}
